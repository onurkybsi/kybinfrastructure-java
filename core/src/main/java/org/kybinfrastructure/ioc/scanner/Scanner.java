package org.kybinfrastructure.ioc.scanner;

import java.util.Set;
import java.util.function.Predicate;

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

	/**
	 * Scans the classes which are located in the directory of given <i>rootClass</i>, or the sub
	 * directories of it. Only the class instances which satisfy the criteria given through the
	 * argument <i>filter</i> are added to the result set.
	 * 
	 * 
	 * @param rootClass <i>rootClass</i> which located in the root directory for the scanning
	 * @param filter criteria which included classes have to satisfy
	 * @return classes in the target directories which satify the given filter condition
	 */
	Set<Class<?>> scan(Class<?> rootClass, Predicate<Class<?>> filter);

}
