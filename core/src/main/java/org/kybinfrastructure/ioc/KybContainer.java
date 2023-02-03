package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import org.kybinfrastructure.ioc.initializer.InitializationConfig;
import org.kybinfrastructure.ioc.initializer.Initializer;
import org.kybinfrastructure.ioc.initializer.InitializerImpl;
import org.kybinfrastructure.ioc.scanner.Scanner;
import org.kybinfrastructure.ioc.scanner.ScannerImpl;
import org.kybinfrastructure.utils.validation.Assertions;
import java.util.HashMap;
import java.util.Set;

/**
 * <a href="https://en.wikipedia.org/wiki/Inversion_of_control">IoC container</a> solution of
 * <i>KybInfrastructure</i>
 */
public final class KybContainer {

	// TODO: Internal implementation should be abstracted
	// An internal factory can provide the implementation by some kind of config like version number.
	private static final Scanner SCANNER = new ScannerImpl();
	private static final DependencyResolver RESOLVER = new DependencyResolverImpl();
	private static final Initializer INITIALIZER = new InitializerImpl();

	private static final HashMap<Class<?>, Object> INSTANCES = new HashMap<>();

	KybContainer(Class<?> rootClass) {
		Set<Class<?>> classesToInit = SCANNER.scan(rootClass, KybContainer::classLoadingFilter);
		RESOLVER.resolve(classesToInit);
		classesToInit
				.forEach(c -> INSTANCES.put(c, INITIALIZER.init(c, InitializationConfig.of(null))));
	}

	public <T> T getImpl(Class<T> classInstance) {
		Assertions.notNull(classInstance, "classInstance cannot be null!");

		Object instance = INSTANCES.get(classInstance);
		if (instance == null) {
			throw new KybInfrastructureException("No implementation found by the given class instance!");
		}

		if (classInstance.isInstance(instance)) {
			return classInstance.cast(instance);
		}

		throw new KybInfrastructureException("Initiated instance couldn't be cast to the actual type!");
	}

	private static boolean classLoadingFilter(Class<?> classToLoad) {
		return !classToLoad.isLocalClass() && !classToLoad.isMemberClass()
				&& classToLoad.getAnnotation(Impl.class) != null;
	}

}
