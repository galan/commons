package de.galan.commons.logging;

import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.Message;


/**
 * Construction of PayloadContextMessages
 */
public class PayloadContextMessageFactory extends AbstractMessageFactory {

	public static final PayloadContextMessageFactory INSTANCE = new PayloadContextMessageFactory();


	@Override
	public Message newMessage(String message, Object... params) {
		return new PayloadContextMessage(message, params);
	}

}
