package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import org.kybinfrastructure.ioc.scanner.ScannerImpl;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * <a href="https://en.wikipedia.org/wiki/Inversion_of_control">IoC container</a> solution of
 * <i>KybInfrastructure</i>
 */
public final class KybContainer {

	// TODO: I don't think that we need a thread-safe array ?
	private static Set<Class<?>> CLASSES = new HashSet<>();
	private static HashMap<Class<?>, Object> INSTANCES = new HashMap<>();

	private Scanner scanner = new ScannerImpl(); // TODO: Another way!

	KybContainer(Class<?> rootClass) {
		try {
			CLASSES = scanner.scan(rootClass);
			filterClassesToResolve();
			initInstances();
		} catch (Exception e) {
			throw new KybInfrastructureException("Loading is not successful", e);
		}
	}

	// #region filtering
	private static void filterClassesToResolve() {
		Set<Class<?>> filtered = new HashSet<>();
		for (Class<?> loadedClass : CLASSES) {
			if (checkWhetherHasImplAnnotation(loadedClass)) {
				filtered.add(loadedClass);
			}
		}
		CLASSES = filtered;
	}

	private static boolean checkWhetherHasImplAnnotation(Class<?> loadedClass) {
		Annotation[] annotations = loadedClass.getAnnotations();
		for (int i = 0; i < annotations.length; i++) {
			if (Impl.class.equals(annotations[i].annotationType())) {
				return true;
			}
		}
		return false;
	}
	// #endregion

	// #region init
	private static void initInstances() {
		for (Class<?> classToInit : CLASSES) {
			try {
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
