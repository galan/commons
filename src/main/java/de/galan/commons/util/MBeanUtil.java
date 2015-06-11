package de.galan.commons.util;

import static org.apache.commons.lang3.StringUtils.*;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import de.galan.commons.logging.Say;


/**
 * MBean helper, registers MBeans with given package-name.
 *
 * @author galan
 */
public class MBeanUtil {

	/**
	 * Will register the given mbean using the given packageName, type and name. If no packageName has been given, the
	 * package of the mbean will be used.
	 */
	public static void registerMBean(String packageName, String type, String name, Object mbean) {
		try {
			String pkg = defaultString(packageName, mbean.getClass().getPackage().getName());
			MBeanServer server = ManagementFactory.getPlatformMBeanServer();
			ObjectName on = new ObjectName(pkg + ":type=" + type + ",name=" + name);
			if (server.isRegistered(on)) {
				Say.debug("MBean with type {type} and name {name} for {package} has already been defined, ignoring", type, name, pkg);
			}
			else {
				server.registerMBean(mbean, on);
			}
		}
		catch (Exception ex) {
			Say.warn("MBean could not be registered", ex);
		}
	}

}
