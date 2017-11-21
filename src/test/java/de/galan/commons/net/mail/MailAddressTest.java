package de.galan.commons.net.mail;

import static org.assertj.core.api.Assertions.*;

import javax.mail.internet.AddressException;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;


/**
 * CUT MailAddress
 */
@RunWith(DataProviderRunner.class)
public class MailAddressTest {

	@Test
	@UseDataProvider
	public void formatValid(String address, String email, String name) throws AddressException {
		MailAddress ma = MailAddress.of(address);
		assertThat(ma.getAddress()).isEqualTo(email);
		assertThat(ma.getName()).isEqualTo(name);
	}


	@DataProvider
	public static Object[][] dataFormatValid() {
		return new Object[][] {
				{"hello@example.com", "hello@example.com", null},
				{"huh+123@example.co.uk", "huh+123@example.co.uk", null},
				{"Mr. Soso <huh+123@example.co.uk>", "huh+123@example.co.uk", "Mr. Soso"},
				{" Some One < some@example.de >", "some@example.de", "Some One"},
				{" Some One < some@1.2.3.4 >", "some@1.2.3.4", "Some One"},
		};
	}


	@Test(expected = AddressException.class)
	@UseDataProvider
	public void formatInvalid(String address) throws AddressException {
		MailAddress.of(address);
	}


	@DataProvider
	public static Object[][] dataFormatInvalid() {
		return new Object[][] {
				{"@example.com"},
				{"Some One"},
				{"Some One <>"},
		};
	}

}
