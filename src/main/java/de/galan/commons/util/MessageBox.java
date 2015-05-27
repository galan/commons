package de.galan.commons.util;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.base.StandardSystemProperty;

import de.galan.commons.logging.Say;


/**
 * Print generic message-boxes to the logging system
 *
 * @author daniel
 */
public class MessageBox {

	private static final int WIDTH = 68;
	private final static Pattern LINEBREAK = Pattern.compile("\\r?\\n");


	/** The overview of the properties will be logged */
	public static void printBox(String title, String message) {
		printBox(title, Splitter.on(LINEBREAK).splitToList(message));
	}


	public static void printBox(String title, List<String> messageLines) {
		Say.info("{message}", generateBox(title, messageLines));
	}


	protected static String generateBox(String title, List<String> messageLines) {
		// see http://en.wikipedia.org/wiki/Box-drawing_character
		String lf = StandardSystemProperty.LINE_SEPARATOR.value();
		String indention = "\t";
		StringBuilder info = new StringBuilder(lf);
		if (isNotBlank(title)) {
			info.append(indention + "╭" + StringUtils.repeat("─", WIDTH) + "╮" + lf);
			info.append(indention + "│" + StringUtils.repeat(" ", WIDTH) + "│" + lf);
			info.append(indention + StringUtils.rightPad("│    " + title, WIDTH + 1, " ") + "│" + lf);
			info.append(indention + "╞════" + StringUtils.repeat("═", WIDTH - 8) + "════╛" + lf);
		}
		else {
			info.append(indention + "╭" + StringUtils.repeat("─", WIDTH) + "┄" + lf);
		}
		info.append(indention + "│" + StringUtils.repeat(" ", WIDTH) + lf);
		for (String line: messageLines) {
			info.append(indention + "│    " + line + lf);
		}
		info.append(indention + "│" + StringUtils.repeat(" ", WIDTH) + lf);
		info.append(indention + "╰" + StringUtils.repeat("─", WIDTH) + "┄" + lf);
		return info.toString();
	}

}
