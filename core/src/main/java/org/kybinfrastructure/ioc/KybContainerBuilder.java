package org.kybinfrastructure.ioc;

import org.kybinfrastructure.utils.validation.Assertions;

/**
 * Contains some utilities to build {@link KybContainer}
 */
public final class KybContainerBuilder {

	private KybContainerBuilder() {}

	/**
	 * Builds by a given root class
	 * 
	 * @param rootClass root class to be used root of the classes which are going to be resolved
	 * @return a fresh instance of {@link KybContainer}
	 */
	public static KybContainer build(Class<?> rootClass) {
		Assertions.notNull(rootClass, "rootClass cannot be null!");
		return new KybContainer(rootClass);
	}

}
