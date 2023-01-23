package org.kybinfrastructure.ioc;

import java.util.Set;

/**
 * Represents the API which scans a classpath to find related classes
 */
public interface Scanner {
	/**
	 * Scans the classes which are located in the directory of given <i>rootClass</i>, or the sub
	 * directories of it.
	 * 
	 * @param rootClass <i>rootClass</i> which located in the root directory for the scanning
	 * @return classes found during the scanning
	 */
	Set<Class<?>> scan(Class<?> rootClass);
}
