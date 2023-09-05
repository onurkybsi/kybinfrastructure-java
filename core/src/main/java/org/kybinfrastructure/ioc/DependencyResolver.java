package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.InvalidDataException;
import java.util.Set;

/**
 * Represents the API which resolves the dependencies between the components
 * 
 * @author Onur Kayabasi (o.kayabasi@outlook.com)
 */
interface DependencyResolver {

	/**
	 * <p>
	 * Resolves the dependencies between the given class set.
	 * </p>
	 * 
	 * @param classesToManage classes to be resolved
	 * @return set of {@link ManagedClass} which has resolved dependecies
	 * @throws InvalidDataException
	 *         <ul>
	 *         <li>When given {@code classesToManage} have a class which has more than 1
	 *         constructor</li>
	 *         <li>When given {@code classesToManage} have a class which has a constructor parameter
	 *         which doesn't exist in the given {@code classesToManage}</li> *
	 *         <li>When given two classes in {@code classesToManage} are depend on each other</li>
	 *         </ul>
	 */
	Set<ManagedClass> resolve(Set<Class<?>> classesToManage);

}
