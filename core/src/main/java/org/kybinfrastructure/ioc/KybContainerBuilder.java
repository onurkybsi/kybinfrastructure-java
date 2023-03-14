package org.kybinfrastructure.ioc;

import org.kybinfrastructure.utils.validation.Assertions;

/**
 * Builder for {@link KybContainer}.
 *
 * @author Onur Kayabasi (onurbpm@outlook.com)
 */
public final class KybContainerBuilder {

	private KybContainerBuilder() {}

	/**
	 * <p>
	 * Builds by a given root class.
	 * </p>
	 * 
	 * @param rootClass root class to be used as the root package for the classes which are managed by
	 *        {@link KybContainer}
	 * @return the built instance of {@link KybContainer}
	 */
	public static KybContainer build(Class<?> rootClass) {
		Assertions.notNull(rootClass, "rootClass cannot be null!");
		return new KybContainer(rootClass);
	}

}
