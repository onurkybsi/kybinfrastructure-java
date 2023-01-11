package org.kybinfrastructure.ioc;

public final class KybContainer {

	private KybContainer() {}

	public static KybContainer build(Class<?> rootClass) {
		return new KybContainer();
	}

}
