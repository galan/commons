package de.galan.commons.test;

import java.util.Date;
import java.util.function.Supplier;

import de.galan.commons.time.Instants;


/**
 * Provides a fixed date given by the constructor.
 *
 * @author daniel
 */
public class FixedDateSupplier implements Supplier<Date> {

	private Date fixed;


	public FixedDateSupplier(String date) {
		this(date, false);
	}


	public FixedDateSupplier(Date date) {
		fixed = date;
	}


	public FixedDateSupplier(String date, boolean utc) {
		fixed = utc ? Instants.dateLocal(date) : Instants.dateUtc(date);
	}


	@Override
	public Date get() {
		return fixed;
	}

}
