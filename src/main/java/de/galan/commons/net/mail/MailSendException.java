package de.galan.commons.net.mail;

/**
 * Exceptions while trying to send an E-Mail.
 *
 * @author galan
 */
public class MailSendException extends Exception {

	public MailSendException(Exception ex) {
		super("Mail could not be send", ex);
	}

}
