package de.galan.commons.net.mail;

import static de.galan.commons.time.Instants.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import de.galan.commons.logging.Say;


/**
 * Simply. Sending. Mails.
 *
 * @author galan
 */
public class MailMessenger {

	private String noreply;
	private boolean smtpAuth;
	private boolean smtpStarttls;
	private String smtpHost;
	private int smtpPort;
	private String smtpUsername;
	private String smtpPassword;
	private String xmailer;


	protected MailMessenger(MailMessengerBuilder builder) {
		noreply = builder.builderNoreply;
		smtpAuth = builder.builderSmtpAuth;
		smtpStarttls = builder.builderSmtpStarttls;
		smtpHost = builder.builderSmtpHost;
		smtpPort = builder.builderSmtpPort;
		smtpUsername = builder.builderSmtpUsername;
		smtpPassword = builder.builderSmtpPassword;
		xmailer = builder.builderXmailer;
	}


	public static MailMessengerBuilder build() {
		return new MailMessengerBuilder();
	}

	/** Builder for sending mails */
	public static class MailMessengerBuilder {

		private String builderNoreply;
		private boolean builderSmtpAuth = true;
		private boolean builderSmtpStarttls = false;
		private String builderSmtpHost;
		private int builderSmtpPort;
		private String builderSmtpUsername;
		private String builderSmtpPassword;
		private String builderXmailer = EMPTY;


		public MailMessengerBuilder noreply(String noreply) {
			builderNoreply = noreply;
			return this;
		}


		public MailMessengerBuilder smtpAuth(boolean smtpAuth) {
			builderSmtpAuth = smtpAuth;
			return this;
		}


		public MailMessengerBuilder smtpStarttls(boolean smtpStarttls) {
			builderSmtpStarttls = smtpStarttls;
			return this;
		}


		public MailMessengerBuilder smtpHost(String smtpHost) {
			builderSmtpHost = smtpHost;
			return this;
		}


		public MailMessengerBuilder smtpPort(int smtpPort) {
			builderSmtpPort = smtpPort;
			return this;
		}


		public MailMessengerBuilder smtpUsername(String smtpUsername) {
			builderSmtpUsername = smtpUsername;
			return this;
		}


		public MailMessengerBuilder smtpPassword(String smtpPassword) {
			builderSmtpPassword = smtpPassword;
			return this;
		}


		public MailMessengerBuilder xmailer(String xmailer) {
			builderXmailer = xmailer;
			return this;
		}


		public MailMessenger create() {
			return new MailMessenger(this);
		}

	}


	public void send(Mail mail) throws MailInvalidException, MailSendException {
		validate(mail);
		deliver(mail);
	}


	protected void deliver(Mail mail) throws MailSendException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", Boolean.toString(smtpAuth));
		props.put("mail.smtp.starttls.enable", Boolean.toString(smtpStarttls));
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);

		try {

			Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					String username = smtpUsername;
					String password = smtpPassword;
					return new PasswordAuthentication(username, password);
				}
			});

			MimeMessage mimeMsg = constructMimeMessage(mail, mailSession);
			Transport.send(mimeMsg);
		}
		catch (Exception ex) {
			throw new MailSendException(ex);
		}
	}


	private void validate(Mail mail) throws MailInvalidException {
		String message = null;
		if (mail == null) {
			message = "Mail is null";
		}
		else if (StringUtils.isBlank(mail.getSubject())) {
			message = "No subject";
		}
		else if (StringUtils.isBlank(mail.getBodyText())) {
			message = "No body";
		}
		else if (!mail.hasRecipients()) {
			message = "No recipients";
		}
		else if (mail.getSender() == null) {
			message = "No sender";
		}

		//TODO Validations.validate(mail);

		if (message != null) {
			throw new MailInvalidException(mail, message);
		}
	}


	private MimeMessage constructMimeMessage(Mail mail, Session mailSession) throws MessagingException {
		MimeMessage mimeMessage = new MimeMessage(mailSession);

		// Subject
		mimeMessage.setSubject(mail.getSubject(), "UTF-8");

		// Recipients
		setRecipients(mimeMessage, mail.getRecipientsTo(), RecipientType.TO);
		setRecipients(mimeMessage, mail.getRecipientsCc(), RecipientType.CC);
		setRecipients(mimeMessage, mail.getRecipientsBcc(), RecipientType.BCC);

		// Sender
		InternetAddress sender = null;
		if (mail.getSender() == null || StringUtils.isBlank(mail.getSender().getAddress())) {
			sender = new InternetAddress(noreply);
		}
		else {
			sender = new InternetAddress(mail.getSender().getCanonical(true));
		}
		mimeMessage.setFrom(sender);

		// ReplyTo
		Address[] replyTo = null;
		if ((mail.getReplyTo() != null) && StringUtils.isNotBlank(mail.getReplyTo().getAddress())) {
			replyTo = new Address[] {new InternetAddress(mail.getReplyTo().getAddress())};
			mimeMessage.setReplyTo(replyTo);
		}

		// Metadata
		mimeMessage.setSentDate(from(now()).toDate());
		mimeMessage.setHeader("Content-Transfer-Encoding", "quoted-printable");
		mimeMessage.addHeader("Auto-Submitted", "auto-generated"); // avoid out-of-office replies, RFC 3834
		mimeMessage.setHeader("X-Mailer", xmailer);

		// Header
		if (mail.hasHeaders()) {
			for (String key: mail.getHeader().keySet()) {
				String name = key;
				String value = mail.getHeader().get(name);
				try {
					// Ensure only 7-Bit values
					name = MimeUtility.encodeText(name, "US-ASCII", "Q");
					value = MimeUtility.encodeText(value, "US-ASCII", "Q");
					mimeMessage.addHeader(name, value);
				}
				catch (UnsupportedEncodingException e) {
					Say.error("Could not add header, name: {name}, value: {value}", name, value);
				}
			}
		}

		// Body
		// Check if a MultiPart Mail has to be constructed
		if (mail.getBodyHtml() != null || mail.hasAttachments()) {
			MimeMultipart content = new MimeMultipart("mixed");

			// Textbody is required
			MimeBodyPart text = new MimeBodyPart();
			text.setText(mail.getBodyText());
			text.setHeader("MIME-Version", "1.0");
			text.setHeader("Content-Type", "text/plain");

			// If html is given as preferred option, an "alternative" part has to be cascaded below the mainpart
			if (mail.getBodyHtml() != null) {
				MimeBodyPart html = new MimeBodyPart();
				html.setContent(mail.getBodyHtml(), "text/html");
				html.setHeader("MIME-Version", "1.0");
				html.setHeader("Content-Type", "text/html");

				MimeMultipart textMultiPart = new MimeMultipart("alternative");
				textMultiPart.addBodyPart(text);
				textMultiPart.addBodyPart(html);

				// The cascade takes place here
				MimeBodyPart textBodyPart = new MimeBodyPart();
				textBodyPart.setContent(textMultiPart);
				content.addBodyPart(textBodyPart);
			}
			else {
				// Only add the text to the mainpart
				content.addBodyPart(text);
			}

			// Add attachments if given
			if (mail.hasAttachments()) {
				for (Attachment attachment: mail.getAttachments()) {
					if (ArrayUtils.isNotEmpty(attachment.getAttachmentData())) {
						BodyPart attachmentPart = new MimeBodyPart();
						DataSource datasource = new AttachmentDataSource(attachment);
						attachmentPart.setDataHandler(new DataHandler(datasource));
						attachmentPart.setFileName(attachment.getFilename());
						attachmentPart.setDisposition("attachment"); // inline only for Outlook
						content.addBodyPart(attachmentPart);
					}
				}
			}

			mimeMessage.setContent(content);
			mimeMessage.setHeader("MIME-Version", "1.0");
			mimeMessage.setHeader("Content-Type", content.getContentType());
		}
		else {
			// Plain text mail without html body and attachments
			mimeMessage.setContent(mail.getBodyText(), "text/plain; charset=\"UTF-8\"");
		}

		return mimeMessage;
	}


	private void setRecipients(MimeMessage mimeMessage, List<MailAddress> recipients, RecipientType type) throws AddressException, MessagingException {
		for (MailAddress address: recipients) {
			mimeMessage.addRecipient(type, new InternetAddress(address.getAddress()));
		}
	}

}
