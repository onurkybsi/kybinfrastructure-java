package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.NotFoundException;
import java.util.Set;

/**
 * <p>
 * <a href="https://en.wikipedia.org/wiki/Inversion_of_control">IoC container</a> solution of
 * <i>KybInfrastructure</i>.
 * </p>
 * 
 * @author Onur Kayabasi (o.kayabasi@outlook.com)
 */
public final class KybContainer {

	private static final Scanner SCANNER = new ScannerClassgraphImpl();
	private static final DependencyResolver RESOLVER = new DependencyResolverImpl();

	private final Container container;

	KybContainer(Class<?> rootClass) {
		Set<Class<?>> classesToInit = SCANNER.scan(rootClass);
		Set<ManagedClass> managedClasses = RESOLVER.resolve(classesToInit);
		this.container = Container.build(managedClasses);
		this.container.init();
	}

	/**
	 * <p>
	 * Returns the <i>KybContainer</i> initialized instance of the given type.
	 * </p>
	 *
	 * @param <T> type of the instance
	 * @param classInstance class instance of the type
	 * @return <i>KybContainer</i> initialized instance
	 * @throws NotFoundException if no instance found in the container by given type
	 */
	public <T> T getImpl(Class<T> classInstance) {
		return container.getImpl(classInstance);
	}

}
