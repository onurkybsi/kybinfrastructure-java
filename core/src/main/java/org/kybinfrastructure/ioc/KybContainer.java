package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.InvalidDataException;
import org.kybinfrastructure.exception.NotFoundException;
import org.kybinfrastructure.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(KybContainer.class);
	private static final Scanner SCANNER = new ScannerClassgraphImpl();
	private static final DependencyResolver RESOLVER = new DependencyResolverImpl();

	private final Container container;

	KybContainer(Class<?> rootClass) {
		LOGGER.debug("KybContainer is being built with root class: {}", rootClass);

		Set<Class<? extends Injector>> injectorClasses = SCANNER.scan(rootClass);
		Set<Class<?>> classesToManage = extractClassesToManage(injectorClasses);
		Set<ManagedClass> managedClasses = RESOLVER.resolve(classesToManage);
		container = Container.build(managedClasses);
		container.init();

		LOGGER.debug("KybContainer was built!");
	}

	public static KybContainerBuilder builder(Class<?> rootClass) {
		return new KybContainerBuilder(rootClass);
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

	/**
	 * Returns all the managed classes.
	 *
	 * @return {@code KybContainer} managed classes
	 */
	public Set<Class<?>> getManagedClasses() {
		return container.getManagedClasses();
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

				Iterable<Class<?>> classesToInject = injectorInstance.inject();
				for (Class<?> classToInject : classesToInject) {
					assertManageable(classToInject);
					LOGGER.debug("{} was injected!", classToInject.getName());
					classesToManage.add(classToInject);
				}
			}
		} catch (NoSuchMethodException e) {
			throw new InvalidDataException("Injector class should have 'only' default constructor!", e);
		} catch (Exception e) {
			throw new UnexpectedException("Managed classes couldn't be extraced!", e);
		}

		return classesToManage;
	}

	private static void assertManageable(Class<?> classToInject) {
		if (classToInject.isInterface()) {
			throw new InvalidDataException("An interface cannot be injected: " + classToInject.getName());
		}
		if (classToInject.isMemberClass()) {
			throw new InvalidDataException(
					"A member class cannot be injected: " + classToInject.getName());
		}
		if (classToInject.isLocalClass()) {
			throw new InvalidDataException(
					"A local class cannot be injected: " + classToInject.getName());
		}
		if (classToInject.isAnonymousClass()) {
			throw new InvalidDataException(
					"An anonymous class cannot be injected: " + classToInject.getName());
		}
	}

}
