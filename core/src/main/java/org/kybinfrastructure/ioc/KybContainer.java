package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.KybInfrastructureException;
import java.lang.reflect.Constructor;
import java.util.HashSet;
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

	// private final Container container;

	KybContainer(Class<?> rootClass) {
		Set<Class<? extends Injector>> injectorClasses = SCANNER.scan(rootClass);
		Set<Class<?>> classesToManage = extractClassesToManage(injectorClasses);
	}

	private static Set<Class<?>> extractClassesToManage(
			Set<Class<? extends Injector>> injectorClasses) {
		HashSet<Class<?>> classesToManage = new HashSet<>();

		try {
			for (Class<? extends Injector> injectorClass : injectorClasses) {
				// TODO: Only returns public constructor, this needs to be solved, we should let the client
				// to define a nonpublic one.
				Constructor<? extends Injector> defaultCtor = injectorClass.getConstructor();
				return null;
			}
		} catch (Exception e) {
			throw new KybInfrastructureException("Managed classes extraction is unsuccessful!", e);
		}

		return classesToManage;
	}

	// /**
	// * <p>
	// * Returns the <i>KybContainer</i> initialized instance of the given type.
	// * </p>
	// *
	// * @param <T> type of the instance
	// * @param classInstance class instance of the type
	// * @return <i>KybContainer</i> initialized instance
	// * @throws NotFoundException if no instance found in the container by given type
	// */
	// public <T> T getImpl(Class<T> classInstance) {
	// return container.getImpl(classInstance);
	// }

}
