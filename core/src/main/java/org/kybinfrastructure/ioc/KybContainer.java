package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import org.kybinfrastructure.ioc.scanner.ScannerImpl;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

/**
 * <a href="https://en.wikipedia.org/wiki/Inversion_of_control">IoC container</a> solution of
 * <i>KybInfrastructure</i>
 */
public final class KybContainer {

	private static final HashMap<Class<?>, Object> INSTANCES = new HashMap<>();

	private Scanner scanner = new ScannerImpl(); // TODO: Another way!

	KybContainer(Class<?> rootClass) {
		try {
			Set<Class<?>> classesToInit =
					scanner.scan(rootClass, classtToLoad -> classtToLoad.getAnnotation(Impl.class) != null);
			initInstances(classesToInit);
		} catch (Exception e) {
			throw new KybInfrastructureException("Loading is not successful", e);
		}
	}

	// #region init
	private static void initInstances(Set<Class<?>> classesToInit) {
		for (Class<?> classToInit : classesToInit) {
			try {
				// 1 - How can we decide to the constructor which is used to init ?
				// 2 - How can we manage scopes ?
				Constructor<?> ctor = classToInit.getConstructors()[0];
				INSTANCES.put(classToInit, ctor.newInstance());
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new KybInfrastructureException("Init is not successful", e);
			}
		}
	}
	// #endregion

	public <T> T getImpl(Class<T> classInstance) {
		Object instance = INSTANCES.get(classInstance);
		if (instance == null) {
			throw new KybInfrastructureException("No implementation found by given class instance!");
		}
		if (classInstance.isInstance(instance)) {
			return (T) instance;
		}
		throw new KybInfrastructureException("No implementation found by given class instance!");
	}

}
