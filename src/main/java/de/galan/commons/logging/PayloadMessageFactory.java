package de.galan.commons.logging;

import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.Message;


/**
 * Construction of PayloadMessages
 *
 * @author daniel
 */
public class PayloadMessageFactory extends AbstractMessageFactory {

	public static final PayloadMessageFactory INSTANCE = new PayloadMessageFactory();


	@Override
	public Message newMessage(String message, Object... params) {
		return new PayloadMessageOld(message, params);
	}

}
