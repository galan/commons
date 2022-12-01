package de.galan.commons.io.resources;

import static java.nio.charset.StandardCharsets.*;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;


public class Resources {

	public static String read(Class<?> base, String name) throws IOException {
		return read(base, name, UTF_8);
	}


	public static String read(Class<?> base, String name, Charset charset) throws IOException {
		URL url = com.google.common.io.Resources.getResource(base, name);
		return com.google.common.io.Resources.toString(url, charset);
	}


	public static List<String> readLines(Class<?> base, String name) throws IOException {
		return readLines(base, name, UTF_8);
	}


	public static List<String> readLines(Class<?> base, String name, Charset charset) throws IOException {
		URL url = com.google.common.io.Resources.getResource(base, name);
		return com.google.common.io.Resources.readLines(url, charset);
	}

}
