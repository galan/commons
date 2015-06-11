package de.galan.commons.net.mail;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;


/**
 * Represents an E-Mail address
 *
 * @author galan
 */
public class MailAddress {

	private String address;
	private String name;


	public MailAddress(String address) {
		this(address, null);
	}


	public MailAddress(String address, String name) {
		this.address = address;
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public String getCanonical() {
		return address;
	}


	public String getCanonical(boolean includeName) {
		String result = getCanonical();
		if (includeName && StringUtils.isNotBlank(getName())) {
			result = getName() + "<" + result + ">";
		}
		return result;
	}


	@Override
	public String toString() {
		return getCanonical(true);
	}


	@Override
	public int hashCode() {
		return Objects.hash(address, name);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MailAddress) {
			final MailAddress other = (MailAddress)obj;
			return new EqualsBuilder().append(address, other.address).append(name, other.name).isEquals();
		}
		return false;
	}

}
