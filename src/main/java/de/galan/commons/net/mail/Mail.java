package de.galan.commons.net.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents an E-Mail to send. Bean-style accessors are available, as well as fluent setters.
 *
 * @author galan
 */
public class Mail {

	public final static int PRIORITY_LOW = 1;
	public final static int PRIORITY_NORMAL = 2;
	public final static int PRIORITY_HIGH = 3;

	private String subject;
	private String body;
	private String bodyHtml;

	private MailAddress sender;
	private MailAddress replyTo;
	private List<MailAddress> recipientsTo = new ArrayList<MailAddress>();
	private List<MailAddress> recipientsCc = new ArrayList<MailAddress>();
	private List<MailAddress> recipientsBcc = new ArrayList<MailAddress>();

	private List<Attachment> attachments = new ArrayList<Attachment>();

	private int priority = PRIORITY_NORMAL;

	private Map<String, String> header = new HashMap<String, String>();


	public Mail() {
		// nada
	}


	public Mail(String sender, String subject, String... recipientsTo) {
		this(new MailAddress(sender), subject, recipientsTo);
	}


	public Mail(MailAddress sender, String subject, String... recipientsTo) {
		setSender(sender);
		setSubject(subject);
		for (String recipient: recipientsTo) {
			addRecipientTo(new MailAddress(recipient));
		}
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public Mail subject(String mailSubject) {
		setSubject(mailSubject);
		return this;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public Mail body(String mailBody) {
		setBody(mailBody);
		return this;
	}


	public String getBodyHtml() {
		return bodyHtml;
	}


	public void setBodyHtml(String bodyHtml) {
		this.bodyHtml = bodyHtml;
	}


	public Mail bodyHtml(String mailBodyHtml) {
		setBodyHtml(mailBodyHtml);
		return this;
	}


	public MailAddress getSender() {
		return sender;
	}


	public void setSender(MailAddress sender) {
		this.sender = sender;
	}


	public Mail sender(MailAddress mailSender) {
		setSender(mailSender);
		return this;
	}


	public MailAddress getReplyTo() {
		return replyTo;
	}


	public void setReplyTo(MailAddress replyTo) {
		this.replyTo = replyTo;
	}


	public Mail replyTo(MailAddress mailReplyTo) {
		setReplyTo(mailReplyTo);
		return this;
	}


	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public Mail priority(int mailPriority) {
		setPriority(mailPriority);
		return this;
	}


	public List<MailAddress> getRecipientsTo() {
		return recipientsTo;
	}


	public List<MailAddress> getRecipientsCc() {
		return recipientsCc;
	}


	public List<MailAddress> getRecipientsBcc() {
		return recipientsBcc;
	}


	public void addRecipientTo(MailAddress address) {
		recipientsTo.add(address);
	}


	public void addRecipientCc(MailAddress address) {
		recipientsCc.add(address);
	}


	public void addRecipientBcc(MailAddress address) {
		recipientsBcc.add(address);
	}


	public Mail to(MailAddress... mailAddress) {
		if (mailAddress != null) {
			for (MailAddress address: mailAddress) {
				addRecipientTo(address);
			}
		}
		return this;
	}


	public Mail cc(MailAddress... mailAddress) {
		if (mailAddress != null) {
			for (MailAddress address: mailAddress) {
				addRecipientCc(address);
			}
		}
		return this;
	}


	public Mail bcc(MailAddress... mailAddress) {
		if (mailAddress != null) {
			for (MailAddress address: mailAddress) {
				addRecipientBcc(address);
			}
		}
		return this;
	}


	public Map<String, String> getHeader() {
		return header;
	}


	public void addHeader(String name, String value) {
		header.put(name, value);
	}


	public Mail header(String name, String value) {
		addHeader(name, value);
		return this;
	}


	public boolean hasHeaders() {
		return !header.isEmpty();
	}


	public int getNumberRecipients() {
		return getRecipientsTo().size() + getRecipientsCc().size() + getRecipientsBcc().size();
	}


	public boolean hasRecipients() {
		return getNumberRecipients() > 0;
	}


	public void addAttachment(Attachment attachment) {
		getAttachments().add(attachment);
	}


	public void addAttachments(List<Attachment> listOfAttachments) {
		getAttachments().addAll(listOfAttachments);
	}


	public Mail attachment(Attachment mailAttachment) {
		addAttachment(mailAttachment);
		return this;
	}


	public void removeAttachment(Attachment attachment) {
		getAttachments().remove(attachment);
	}


	public void removeAllAttachments() {
		getAttachments().clear();
	}


	public List<Attachment> getAttachments() {
		return attachments;
	}


	public boolean hasAttachments() {
		return !getAttachments().isEmpty();
	}


	@Override
	public String toString() {
		return getSender() + ": " + getSubject();
	}

}
