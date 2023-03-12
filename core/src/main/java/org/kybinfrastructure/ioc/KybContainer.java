package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import org.kybinfrastructure.ioc.scanner.Scanner;
import org.kybinfrastructure.ioc.scanner.ScannerImpl;
import org.kybinfrastructure.utils.validation.Assertions;
import java.util.HashMap;
import java.util.Set;

/**
 * <a href="https://en.wikipedia.org/wiki/Inversion_of_control">IoC container</a> solution of
 * <i>KybInfrastructure</i>
 * 
 * @author Onur Kayabasi (onurbpm@outlook.com)
 */
public final class KybContainer {

	// TODO: Internal implementation should be abstracted ?
	private static final Scanner scanner = new ScannerImpl();
	private static final DependencyResolver resolver = new DependencyResolverImpl();
	private final Initializer initializer;

	private static final HashMap<Class<?>, Object> INSTANCES = new HashMap<>();

	KybContainer(Class<?> rootClass) {
		Set<Class<?>> classesToInit = scanner.scan(rootClass, KybContainer::classLoadingFilter);
		Set<ManagedClass> managedClasses = resolver.resolve(classesToInit);
		initializer = InitializerImpl.of(managedClasses);
		classesToInit.forEach(
				c -> INSTANCES.put(c, initializer.init(c, new InitializationConfig(INSTANCES, true))));
	}

	/**
	 * Returns the <i>KybContainer</i> initialized instance of the given type.
	 *
	 * @param <T> type of the instance
	 * @param classInstance class instance of the type
	 * @return <i>KybContainer</i> initialized instance
	 * @throws KybInfrastructureException if no instance found in the container by given type
	 */
	public <T> T getImpl(Class<T> classInstance) {
		Assertions.notNull(classInstance, "classInstance cannot be null!");

		Object instance = INSTANCES.get(classInstance);
		if (instance == null) {
			// TODO: Should we use exception type like NoImpFound extends KybInfrastructureException ?
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
