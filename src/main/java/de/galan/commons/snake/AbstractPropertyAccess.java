package de.galan.commons.snake;

import java.util.ArrayList;
import java.util.List;


/**
 * Abstract implementation for PropertyAccess, defaultimplementations for listeners and system.
 *
 * @author daniel
 */
public abstract class AbstractPropertyAccess implements PropertyAccess {

	private List<SnakeListener> listeners;
	private SystemModel system;


	public AbstractPropertyAccess() {
		system = new SystemModel();
		listeners = new ArrayList<>();
	}


	protected List<SnakeListener> getListeners() {
		return listeners;
	}


	@Override
	public void addListener(SnakeListener listener) {
		getListeners().add(listener);
	}


	@Override
	public void removeListener(SnakeListener listener) {
		getListeners().remove(listener);
	}


	protected void notifyListenerRefreshed() {
		for (SnakeListener listener: getListeners()) {
			listener.propertiesRefreshed();
		}
	}


	protected void notifyPropertyAdded(String name, String value) {
		for (SnakeListener listener: getListeners()) {
			listener.propertyAdded(name, value);
		}
	}


	protected void notifyPropertyModified(String name, String valueOld, String valueNew) {
		for (SnakeListener listener: getListeners()) {
			listener.propertyModified(name, valueOld, valueNew);
		}
	}


	protected void notifyPropertyRemoved(String name, String value) {
		for (SnakeListener listener: getListeners()) {
			listener.propertyDeleted(name, value);
		}
	}


	@Override
	public SystemModel system() {
		return system;
	}

}
