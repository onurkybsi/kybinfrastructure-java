package org.kybinfrastructure.ioc;

import java.lang.reflect.Method;

/**
 * Wrapper for the classes managed by {@link KybContainer}.
 */
final class ManagedClass {

	private final Class<?> clazz;
	private final Object injectorInstance;
	private final Method factoryMethod;

	ManagedClass(Class<?> clazz, Object injectorInstance, Method factoryMethod) {
		this.clazz = clazz;
		this.injectorInstance = injectorInstance;
		this.factoryMethod = factoryMethod;
	}

	Class<?> getClazz() {
		return clazz;
	}

	Object getInjectorInstance() {
		return injectorInstance;
	}

	Method getFactoryMethod() {
		return factoryMethod;
	}

	@Override
	public String toString() {
		return "ManagedClass(clazz=%s,injectorInstance=%s,factoryMethod=%s)".formatted(clazz,
				injectorInstance.getClass().getName(), factoryMethod.getName());
	}

}
