package de.galan.commons.time;

import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.StringUtils.*;
import static org.assertj.core.api.Assertions.*;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Stopwatch;

import de.galan.commons.logging.Say;
import de.galan.commons.test.AbstractTestParent;


/**
 * Generation of zones and tests.
 */
public class ZonesTest extends AbstractTestParent {

	@Test
	public void accessZones() throws Exception {
		assertThat(Zones.europeBerlin()).isEqualTo(ZoneId.of("Europe/Berlin"));
	}


	@Test
	public void performance() throws Exception {
		Zones.europeAmsterdam(); // warmup
		Stopwatch watch = Stopwatch.createStarted();
		for (int i = 0; i < 10_000; i++) {
			Zones.europeAmsterdam();
		}
		Say.info("Took {}", watch.stop().elapsed(TimeUnit.MILLISECONDS));
		// Took 2ms on developer machine with warmup (just lookups), 62ms without warmup (no fixed reference available)
	}


	@Test
	public void generate() throws Exception {
		List<String> methods = new ArrayList<>();
		List<String> ids = ZoneId.getAvailableZoneIds().stream().sorted().collect(toList());
		for (String id: ids) {
			String methodName = id;
			if (!id.contains("/")) {
				methodName = methodName.toLowerCase();
			}
			methodName = methodName.replace("Etc/GMT+", "etcGmtPlus");
			methodName = methodName.replace("Etc/GMT-", "etcGmtMinus");
			methodName = methodName.replace("Etc/GMT0", "etcGmt0");
			methodName = methodName.replace("Etc/GMT", "etcGmt");
			if (methodName.startsWith("US/")) {
				methodName = methodName.replace("US/", "us/");
			}
			methodName = uncapitalize(methodName);
			methodName = replaceChar(methodName, "/");
			methodName = replaceChar(methodName, "-");
			methodName = replaceChar(methodName, "_");
			//Say.info("X: {}\t{}", methodName, id);
			methods.add("public static ZoneId " + methodName + "() {\n	return getZoneId(\"" + id + "\");  \n	}");
		}

		String generated = methods.stream().collect(joining("\n"));

		//Enable to generate:
		//Say.info("Ids: {}", generated);
	}


	private String replaceChar(String input, String replace) {
		String result = input;
		int idx = input.indexOf(replace);
		while(idx != -1) {
			String replacement = result.substring(idx + 1, idx + 2).toUpperCase();
			result = new StringBuilder(result).replace(idx, idx + 2, replacement).toString();
			idx = result.indexOf(replace);
		}
		return result;
	}

}
