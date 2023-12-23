package org.kybinfrastructure.ioc;

import org.kybinfrastructure.utils.validation.Assertions;

/**
 * Builder for {@link KybContainer}.
 *
 * @author Onur Kayabasi (o.kayabasi@outlook.com)
 */
final class KybContainerBuilder {

	private final Class<?> rootClass;

	KybContainerBuilder(Class<?> rootClass) {
		Assertions.notNull(rootClass, "rootClass cannot be null!");

		this.rootClass = rootClass;
	}

	/**
	 * Builds a fresh {@link KybContainer} with given properties.
	 *
	 * @return built {@link KybContainer} instance
	 */
	public KybContainer build() {
		return new KybContainer(rootClass);
	}

}
