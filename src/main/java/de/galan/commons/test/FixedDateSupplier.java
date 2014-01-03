package de.galan.commons.test;

import java.util.Date;

import de.galan.commons.time.DateDsl;
import de.galan.commons.time.DateSupplier;


/**
 * Provides a fixed date given by the constructor.
 * 
 * @author daniel
 */
public class FixedDateSupplier implements DateSupplier {

	private Date fixed;


	public FixedDateSupplier(String date) {
		this(date, false);
	}


	public FixedDateSupplier(Date date) {
		fixed = date;
	}


	public FixedDateSupplier(String date, boolean iso) {
		fixed = iso ? DateDsl.dateIso(date) : DateDsl.date(date);
	}


	@Override
	public Date get() {
		return fixed;
	}

}
