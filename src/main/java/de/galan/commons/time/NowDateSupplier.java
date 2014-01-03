package de.galan.commons.time;

import java.util.Date;


/**
 * Provides the current Date
 * 
 * @author daniel
 */
public class NowDateSupplier implements DateSupplier {

	@Override
	public Date get() {
		return new Date();
	}

}
