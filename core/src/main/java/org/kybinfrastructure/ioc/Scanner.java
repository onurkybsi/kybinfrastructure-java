package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.UnexpectedException;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Represents the API which scans some locations to load the related classes
 * 
 * @author Onur Kayabasi (onurbpm@outlook.com)
 */
public interface Scanner {

	/**
	 * <p>
	 * Loads the classes which are located in the directory of given {@code rootClass}, or the sub
	 * directories of it.
	 * </p>
	 * 
	 * @param rootClass class which is located in the root directory for the scanning
	 * @return classes found during the scanning
	 * @throws UnexpectedException when an exception occurred during scanning
	 */
	Set<Class<?>> scan(Class<?> rootClass);

	/**
	 * <p>
	 * Loads the classes which are located in the directory of given {@code rootClass}, or the sub
	 * directories of it.
	 * </p>
	 * <p>
	 * Only the class instances which satisfy the criteria given through the parameter {@code filter}
	 * are added to the result set.
	 * </p>
	 * 
	 * 
	 * @param rootClass class which is located in the root directory for the scanning
	 * @param filter criteria which the result set included classes have to satisfy
	 * @return classes found during the scanning
	 * @throws UnexpectedException when an exception occurred during scanning
	 */
	Set<Class<?>> scan(Class<?> rootClass, Predicate<Class<?>> filter);

}
