package de.galan.commons.net.mail;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

import de.galan.commons.util.Validations;


/**
 * CUT Mail
 */
public class MailTest {

	@Test
	public void validationPlain() throws Exception {
		assertThrows(ConstraintViolationException.class, () -> {
			Mail mail = new Mail();
			Validations.validate(mail);
		});
	}


	@Test
	public void validationMissingRecipients() throws Exception {
		assertThrows(ConstraintViolationException.class, () -> {
			Mail mail = new Mail();
			mail.setSubject("subject");
			mail.setBodyText("bla");
			mail.from(new MailAddress("a@example.com"));
			Validations.validate(mail);
		});
	}


	@Test
	public void validationMissingSubject() throws Exception {
		assertThrows(ConstraintViolationException.class, () -> {
			Mail mail = new Mail();
			mail.setBodyText("bla");
			mail.from(new MailAddress("a@example.com"));
			mail.addRecipientTo(new MailAddress("b@example.com"));
			Validations.validate(mail);
		});
	}


	@Test
	public void validationMissingBody() throws Exception {
		assertThrows(ConstraintViolationException.class, () -> {
			Mail mail = new Mail();
			mail.setSubject("subject");
			mail.from(new MailAddress("a@example.com"));
			mail.addRecipientTo(new MailAddress("b@example.com"));
			Validations.validate(mail);
		});
	}


	@Test
	public void validationMissingFrom() throws Exception {
		assertThrows(ConstraintViolationException.class, () -> {
			Mail mail = new Mail();
			mail.setSubject("subject");
			mail.setBodyText("bla");
			mail.addRecipientTo(new MailAddress("b@example.com"));
			Validations.validate(mail);
		});
	}


	@Test
	public void validationOkTo() throws Exception {
		Mail mail = new Mail();
		mail.setSubject("subject");
		mail.setBodyText("bla");
		mail.from(new MailAddress("a@example.com"));
		mail.addRecipientTo(new MailAddress("b@example.com"));
		Validations.validate(mail);
	}


	@Test
	public void validationOkCc() throws Exception {
		Mail mail = new Mail();
		mail.setSubject("subject");
		mail.setBodyText("bla");
		mail.from(new MailAddress("a@example.com"));
		mail.addRecipientCc(new MailAddress("b@example.com"));
		Validations.validate(mail);
	}


	@Test
	public void validationOkBcc() throws Exception {
		Mail mail = new Mail();
		mail.setSubject("subject");
		mail.setBodyText("bla");
		mail.from(new MailAddress("a@example.com"));
		mail.addRecipientBcc(new MailAddress("b@example.com"));
		Validations.validate(mail);
	}


	@Test
	public void deserializeWithHeaders() throws Exception {
		Mail mail = new Mail();
		mail.addHeader("foo", "bar");
		mail.addHeader("foo", "baz");

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());

		String json = mapper.writeValueAsString(mail);

		Mail actual = mapper.readValue(json, Mail.class);

		assertThat(actual.hasHeaders()).isTrue();
		assertThat(actual.getHeader().get("foo")).contains("bar", "baz");
	}
}
