package de.galan.commons.net.mail;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents an E-Mail address
 *
 * @author galan
 */
public class MailAddress {

	@NotNull
	@JsonProperty("address")
	private String address;
	@JsonProperty("name")
	private String name;


	public MailAddress(String address) {
		this(address, null);
	}


	@JsonCreator
	public MailAddress(@JsonProperty("address") String address, @JsonProperty("name") String name) {
		this.address = trimToNull(address);
		this.name = trimToNull(name);
	}


	public String getName() {
		return name;
	}


	public String getAddress() {
		return address;
	}


	public String getCanonical(boolean includeName) {
		String result = getAddress();
		if (includeName && isNotBlank(getName())) {
			result = getName() + " <" + result + ">";
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
