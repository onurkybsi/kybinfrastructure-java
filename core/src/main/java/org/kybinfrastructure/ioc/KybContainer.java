package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import java.util.Set;

/**
 * <a href="https://en.wikipedia.org/wiki/Inversion_of_control">IoC container</a> solution of
 * <i>KybInfrastructure</i>
 * 
 * @author Onur Kayabasi (onurbpm@outlook.com)
 */
public final class KybContainer {

	private static final Scanner SCANNER = new ScannerImpl();
	private static final DependencyResolver RESOLVER = new DependencyResolverImpl();

	private final Initializer initializer;

	KybContainer(Class<?> rootClass) {
		Set<Class<?>> classesToInit = SCANNER.scan(rootClass, KybContainer::classLoadingFilter);
		Set<ManagedClass> managedClasses = RESOLVER.resolve(classesToInit);
		this.initializer = Initializer.build(managedClasses);
		this.initializer.init();
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
		return initializer.getImpl(classInstance);
	}

	private static boolean classLoadingFilter(Class<?> classToLoad) {
		return !classToLoad.isLocalClass() && !classToLoad.isMemberClass()
				&& classToLoad.getAnnotation(Impl.class) != null;
	}

}
