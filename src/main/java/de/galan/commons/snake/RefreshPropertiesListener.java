package de.galan.commons.snake;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.util.Map;
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
		refresh();
	}


	@Override
	public void notifyFileChanged(File f) {
		refresh();
	}


	@Override
	public void notifyFileDeleted(File f) {
		refresh();
	}


	public void refresh() {
		Map<String, String> properties = source.get();
		for (String key: properties.keySet()) {
			if (!defaults.containsKey(key)) {
				// Whitespaces may lead to unrecognized properties
				String property = trimToEmpty(properties.get(key));
				String current = access.get(property);
				if (!StringUtils.equals(current, property)) {
					access.set(key, property);
					//setQuietly((String)key, property);
				}
			}
		}
		access.printProperties();
		access.notifyRefreshed();
	}

}
