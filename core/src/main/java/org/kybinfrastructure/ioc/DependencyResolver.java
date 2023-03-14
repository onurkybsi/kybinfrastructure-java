package org.kybinfrastructure.ioc;

import java.util.Set;

/**
 * Represents the API which resolves the dependencies between the components
 * 
 * @author Onur Kayabasi (onurbpm@outlook.com)
 */
interface DependencyResolver {

	/**
	 * <p>
	 * Resolves the dependencies between the given class set.
	 * </p>
	 * 
	 * @param classesToManage classes to be resolved
	 * @return set of {@link ManagedClass} which has resolved dependecies
	 */
	Set<ManagedClass> resolve(Set<Class<?>> classesToManage);

}
