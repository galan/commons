package de.galan.commons.snake;

/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public abstract class AbstractPropertyAccess implements PropertyAccess {

	SystemModel system;
	InstanceModel instance;


	public AbstractPropertyAccess() {
		system = new SystemModel();
		instance = new InstanceModel(this);
	}


	@Override
	public SystemModel getSystem() {
		return system;
	}


	@Override
	public InstanceModel getInstance() {
		return instance;
	}

}
