package org.kybinfrastructure.ioc;

import org.kybinfrastructure.utils.validation.Assertions;

/**
 * Builder for {@link KybContainer}.
 *
 * @author Onur Kayabasi (o.kayabasi@outlook.com)
 */
public final class KybContainerBuilder {

	private KybContainerBuilder() {}

	/**
	 * <p>
	 * Builds by a given root class.
	 * </p>
	 * 
	 * @param rootClass Root class to be used as the root package for the classes which are managed by
	 *        {@link KybContainer}.
	 *        <p>
	 *        The classes which are in the package of the {@code rootClass} or the classes in the
	 *        subpackages of the {@code rootClass} are managed by {@link KybContainer}.
	 *        </p>
	 * @return the built instance of {@link KybContainer}
	 */
	public static KybContainer build(Class<?> rootClass) {
		Assertions.notNull(rootClass, "rootClass cannot be null!");
		return new KybContainer(rootClass);
	}

}
