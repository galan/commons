package de.galan.commons.net.mail;

/**
 * Priority of a mail.
 */
public enum MailPriority {

	LOW(1),
	NORMAL(2),
	HIGH(3);

	private int priority;


	private MailPriority(int priority) {
		this.priority = priority;
	}


	public int getPriority() {
		return priority;
	}

}
