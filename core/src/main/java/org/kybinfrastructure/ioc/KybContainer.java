package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
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

	private static final HashMap<Class<?>, Object> INSTANCES = new HashMap<>();

	private static final Scanner SCANNER = new ScannerImpl(); // TODO: Another way!
	private static final Initializer INITIALIZER = new InitializerImpl(); // TODO: Another way!

	KybContainer(Class<?> rootClass) {
		try {
			Set<Class<?>> classesToInit =
					SCANNER.scan(rootClass, classtToLoad -> classtToLoad.getAnnotation(Impl.class) != null);
			classesToInit.forEach(c -> INSTANCES.put(c, INITIALIZER.init(c)));
		} catch (Exception e) {
			throw new KybInfrastructureException("Container coulnd't be constructed!", e);
		}
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

}
