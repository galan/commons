package de.galan.commons.snake;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.Map;
import java.util.Set;

import de.galan.commons.snake.util.SnakeListener;


/**
 * Facade to access Snake properties.
 *
 * @author daniel
 */
public class Snake {

	private static SnakeModel model;


	public static void setSnakeModel(SnakeModel model) {
		Snake.model = model;
	}


	protected static SnakeModel getModel() {
		return model;
	}


	public static Map<String, String> getProperties() {
		return getModel().getProperties();
	}


	public static Set<String> getProperties(String prefix) {
		return getModel().getProperties(prefix);
	}


	public static boolean isSet(String name) {
		return isNotBlank(getModel().get(name));
	}


	public static String get(String name) {
		return getModel().get(name);
	}


	public static String get(String name, String fallback) {
		return getModel().get(name, fallback);
	}


	public static Integer getInt(String name) {
		return getModel().getInt(name);
	}


	public static Integer getInt(String name, Integer fallback) {
		return getModel().getInt(name, fallback);
	}


	public static Double getDouble(String name) {
		return getDouble(name);
	}


	public static Double getDouble(String name, Double fallback) {
		return getDouble(name, fallback);
	}


	public static Long getLong(String name) {
		return getLong(name);
	}


	public static Long getLong(String name, Long fallback) {
		return getLong(name, fallback);
	}


	public static boolean getBool(String name) {
		return getBool(name, false);
	}


	public static boolean getBool(String name, boolean fallback) {
		return getModel().getBool(name, fallback);
	}


	public static Long getTime(String name) {
		return getModel().getTime(name);
	}


	public static Long getTime(String name, String fallback) {
		return getModel().getTime(name, fallback);
	}


	public static String getInstance() {
		return getModel().getInstance();
	}


	public static String getDirectoryBase() {
		return getModel().getDirectoryBase();
	}


	public static String getDirectoryInstance() {
		return getModel().getDirectoryInstance();
	}


	public static String getDirectoryStorage() {
		return getModel().getDirectoryStorage();
	}


	public static String getDirectoryStorage(String subdirectory) {
		return getModel().getDirectoryStorage(subdirectory);
	}


	public static String getDirectoryLog() {
		return getModel().getDirectoryLog();
	}


	public static String getDirectoryConfiguration() {
		return getModel().getDirectoryConfiguration();
	}


	public static String getDirectoryTemp() {
		return getModel().getDirectoryTemp();
	}


	public static String getDirectoryScript() {
		return getModel().getDirectoryScript();
	}


	/** Logs an overview of the properties */
	public static void printProperties() {
		getModel().printProperties();
	}


	public static void addListener(SnakeListener listener) {
		getModel().addListener(listener);
	}


	public static void removeListener(SnakeListener listener) {
		getModel().removeListener(listener);
	}

}
