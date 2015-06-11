package de.galan.commons.net.mail;

/**
 * Signals, that the mail does not match the expected form, eg. fields are missing.
 *
 * @author galan
 */
public class MailInvalidException extends Exception {

	public MailInvalidException(Mail mail, String message) {
		super("Mail is not valid: " + message);
	}

}
