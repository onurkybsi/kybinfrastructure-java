package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.KybInfrastructureException;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * <p>
 * <a href="https://en.wikipedia.org/wiki/Inversion_of_control">IoC container</a> solution of
 * <i>KybInfrastructure</i>.
 * </p>
 * 
 * @author Onur Kayabasi (o.kayabasi@outlook.com)
 */
public final class KybContainer {

	private static final Logger LOGGER = Logger.getLogger(KybContainer.class.getName());

	private static final Scanner SCANNER = new ScannerClassgraphImpl();
	private static final DependencyResolver RESOLVER = new DependencyResolverImpl();

	// private final Container container;

	KybContainer(Class<?> rootClass) {
		Set<Class<? extends Injector>> injectorClasses = SCANNER.scan(rootClass);
		Set<Class<?>> classesToManage = extractClassesToManage(injectorClasses);
		LOGGER.info("{} classes will be managed...".formatted(classesToManage.size()));
	}

	@SuppressWarnings({"java:S3011"})
	private static Set<Class<?>> extractClassesToManage(
			Set<Class<? extends Injector>> injectorClasses) {
		HashSet<Class<?>> classesToManage = new HashSet<>();

		try {
			for (Class<? extends Injector> injectorClass : injectorClasses) {
				if (injectorClass.getConstructors().length != 1) {
					throw new KybInfrastructureException(
							"Injector class should have only default constructor!");
				}
				Constructor<? extends Injector> defaultCtor = injectorClass.getDeclaredConstructor();
				defaultCtor.setAccessible(true);

				Injector injectorInstance = defaultCtor.newInstance();
				Iterable<Class<?>> injectedClasses = injectorInstance.inject();
				for (Class<?> injectedClass : injectedClasses) {
					assertClassManageable(injectedClass);
					classesToManage.add(injectedClass);
				}
			}
		} catch (NoSuchMethodException e) {
			throw new KybInfrastructureException("Injector class should have only default constructor!",
					e);
		} catch (Exception e) {
			throw new KybInfrastructureException("Managed classes extraction is unsuccessful!", e);
		}

		return classesToManage;
	}

	private static void assertClassManageable(Class<?> injectedClass) {
		// TODO:
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
