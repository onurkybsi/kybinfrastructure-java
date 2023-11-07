package org.kybinfrastructure.ioc;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * Wrapper for the classes managed by {@link KybContainer}.
 */
final class ManagedClass {

	private final Class<?> clazz;
	private final Constructor<?> ctr;
	private final Class<?>[] ctrParams;

	ManagedClass(Class<?> clazz, Constructor<?> constructor) {
		this.clazz = clazz;
		this.ctr = constructor;
		this.ctrParams = constructor.getParameterTypes();
	}

	Class<?> getClazz() {
		return clazz;
	}

	Constructor<?> getCtr() {
		return ctr;
	}

	Class<?>[] getCtrParams() {
		return ctrParams;
	}

	@Override
	public String toString() {
		return "ManagedClass(clazz=%s,ctr=%s,ctrParams=%s)".formatted(clazz, ctr,
				Arrays.toString(ctrParams));
	}

}
