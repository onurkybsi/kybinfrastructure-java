package org.kybinfrastructure.ioc;

import java.lang.reflect.Constructor;

/**
 * Internal wrapper for the types which are managed by {@link KybContainer}
 */
final class ManagedClass {

	private final Class<?> clazz;
	private final Constructor<?> ctr;
	private final Class<?>[] ctrParams;

	ManagedClass(Class<?> classToManage, Constructor<?> constructor) {
		this.clazz = classToManage;
		this.ctr = constructor;
		this.ctrParams = constructor.getParameterTypes();
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Constructor<?> getCtr() {
		return ctr;
	}

	public Class<?>[] getCtrParams() {
		return ctrParams;
	}

}
