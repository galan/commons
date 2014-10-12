package de.galan.commons.snake;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableMap;

import de.galan.commons.io.file.FileListener;
import de.galan.commons.snake.access.PropertyAccess;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class RefreshPropertiesListener implements FileListener {

	private PropertyAccess access;
	private Map<String, String> defaults;

	private Supplier<Map<String, String>> source;


	public RefreshPropertiesListener(PropertyAccess access, Supplier<Map<String, String>> source) {
		this.access = access;
		this.source = source;
		defaults = ImmutableMap.copyOf(access.getProperties());
	}


	@Override
	public void notifyFileCreated(File f) {
		if (isTrackingAll()) {
			refresh();
		}
	}


	@Override
	public void notifyFileChanged(File f) {
		refresh();
	}


	@Override
	public void notifyFileDeleted(File f) {
		if (isTrackingAll()) {
			refresh();
		}
	}


	// http://stackoverflow.com/questions/607435/why-does-vim-save-files-with-a-extension
	protected boolean isTrackingAll() {
		String modificationsOnly = access.get("snake.track.anychange");
		return "true".equals(modificationsOnly);
	}


	public void refresh() {
		Predicate<String> filterDefaults = (k) -> !defaults.containsKey(k);
		Map<String, String> properties = source.get();
		properties.keySet().stream().filter(filterDefaults).forEach((k) -> {
			// Whitespaces may lead to unrecognized properties
			String valueNew = trimToEmpty(properties.get(k));
			String valueCurrent = access.get(k);
			if (!StringUtils.equals(valueCurrent, valueNew)) {
				access.set(k, valueNew); //TODO setQuietly((String)key, property); ?
			}
		});

		// Remove missing properties
		access.getProperties().keySet().stream().filter(filterDefaults).filter((k) -> !properties.containsKey(k)).forEach((k) -> access.remove(k));

		access.printProperties();
		access.notifyRefreshed();
	}
}
