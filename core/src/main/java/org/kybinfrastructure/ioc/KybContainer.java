package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.InvalidDataException;
import org.kybinfrastructure.exception.KybInfrastructureException;
import org.kybinfrastructure.exception.NotFoundException;
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

	private final Container container;

	KybContainer(Class<?> rootClass) {
		Set<Class<? extends Injector>> injectorClasses = SCANNER.scan(rootClass);
		Set<Class<?>> classesToManage = extractClassesToManage(injectorClasses);
		Set<ManagedClass> managedClasses = RESOLVER.resolve(classesToManage);
		container = Container.build(managedClasses);
		container.init();
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
	public <T> T get(Class<T> classInstance) {
		return container.get(classInstance);
	}

	@SuppressWarnings({"java:S3011"})
	private static Set<Class<?>> extractClassesToManage(
			Set<Class<? extends Injector>> injectorClasses) {
		HashSet<Class<?>> classesToManage = new HashSet<>();

		try {
			for (Class<? extends Injector> injectorClass : injectorClasses) {
				Constructor<? extends Injector> defaultCtor = injectorClass.getDeclaredConstructor();
				defaultCtor.setAccessible(true);

				Injector injectorInstance = defaultCtor.newInstance();
				Iterable<Class<?>> injectedClasses = injectorInstance.inject();
				for (Class<?> injectedClass : injectedClasses) {
					assertManageable(injectedClass);
					classesToManage.add(injectedClass);
				}
			}
		} catch (NoSuchMethodException e) {
			throw new KybInfrastructureException("Injector class should have 'only' default constructor!",
					e);
		} catch (Exception e) {
			throw new KybInfrastructureException("Managed classes extraction is unsuccessful!", e);
		}

		return classesToManage;
	}

	private static void assertManageable(Class<?> injectedClass) {
		if (injectedClass.isInterface()) {
			throw new InvalidDataException("An interface cannot be injected: " + injectedClass.getName());
		}
		if (injectedClass.isMemberClass()) {
			throw new InvalidDataException(
					"A member class cannot be injected: " + injectedClass.getName());
		}
		if (injectedClass.isLocalClass()) {
			throw new InvalidDataException(
					"A local class cannot be injected: " + injectedClass.getName());
		}
		if (injectedClass.isAnonymousClass()) {
			throw new InvalidDataException(
					"An anonymous class cannot be injected: " + injectedClass.getName());
		}
	}

}
