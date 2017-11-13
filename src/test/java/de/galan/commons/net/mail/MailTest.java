package de.galan.commons.net.mail;

import javax.validation.ConstraintViolationException;

import org.junit.Test;

import de.galan.commons.util.Validations;


/**
 * CUT Mail
 */
public class MailTest {

	@Test(expected = ConstraintViolationException.class)
	public void validationPlain() throws Exception {
		Mail mail = new Mail();
		Validations.validate(mail);
	}


	@Test(expected = ConstraintViolationException.class)
	public void validationMissingRecipients() throws Exception {
		Mail mail = new Mail();
		mail.setSubject("subject");
		mail.setBodyText("bla");
		mail.from(new MailAddress("a@example.com"));
		Validations.validate(mail);
	}


	@Test(expected = ConstraintViolationException.class)
	public void validationMissingSubject() throws Exception {
		Mail mail = new Mail();
		mail.setBodyText("bla");
		mail.from(new MailAddress("a@example.com"));
		mail.addRecipientTo(new MailAddress("b@example.com"));
		Validations.validate(mail);
	}


	@Test(expected = ConstraintViolationException.class)
	public void validationMissingBody() throws Exception {
		Mail mail = new Mail();
		mail.setSubject("subject");
		mail.from(new MailAddress("a@example.com"));
		mail.addRecipientTo(new MailAddress("b@example.com"));
		Validations.validate(mail);
	}


	@Test(expected = ConstraintViolationException.class)
	public void validationMissingFrom() throws Exception {
		Mail mail = new Mail();
		mail.setSubject("subject");
		mail.setBodyText("bla");
		mail.addRecipientTo(new MailAddress("b@example.com"));
		Validations.validate(mail);
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

}
