package de.galan.commons.net.mail;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import javax.mail.internet.AddressException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


/**
 * CUT MailAddress
 */
public class MailAddressTest {

	@ParameterizedTest
	@MethodSource("dataFormatValid")
	public void formatValid(DateItem item) throws AddressException {
		MailAddress ma = MailAddress.of(item.address);
		assertThat(ma.getAddress()).isEqualTo(item.email);
		assertThat(ma.getName()).isEqualTo(item.name);
	}


	public static Stream<DateItem> dataFormatValid() {
		return Stream.of(
			new DateItem("hello@example.com", "hello@example.com", null),
			new DateItem("huh+123@example.co.uk", "huh+123@example.co.uk", null),
			new DateItem("Mr. Soso <huh+123@example.co.uk>", "huh+123@example.co.uk", "Mr. Soso"),
			new DateItem(" Some One < some@example.de >", "some@example.de", "Some One"),
			new DateItem(" Some One < some@1.2.3.4 >", "some@1.2.3.4", "Some One"));
	}


	@ParameterizedTest
	@MethodSource("dataFormatInvalid")
	public void formatInvalid(String address) {
		assertThrows(AddressException.class, () -> {
			MailAddress.of(address);
		});
	}


	public static Stream<String> dataFormatInvalid() {
		return Stream.of("@example.com", "Some One", "Some One <>");
	}

}


class DateItem {

	public DateItem(String address, String email, String name) {
		this.address = address;
		this.email = email;
		this.name = name;
	}

	String address;
	String email;
	String name;
}
