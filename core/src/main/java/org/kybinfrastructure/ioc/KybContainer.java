package org.kybinfrastructure.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.kybinfrastructure.exception.InvalidDataException;
import org.kybinfrastructure.exception.NotFoundException;
import org.kybinfrastructure.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		Set<Class<?>> injectorClasses = SCANNER.scan(rootClass);
		Map<Object, Set<Method>> injectorsWithInjectionMethods =
				extractInjectorsWithInjectionMethods(injectorClasses);
		Set<ManagedClass> managedClasses = RESOLVER.resolve(injectorsWithInjectionMethods);
		container = new Container(managedClasses);
		container.init();

		LOGGER.debug("KybContainer was built!");
	}

	// #region Public API
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
	// #endregion

	private static Map<Object, Set<Method>> extractInjectorsWithInjectionMethods(
			Set<Class<?>> injectorClasses) {
		Map<Object, Set<Method>> injectorsWithInjectionMethods = new LinkedHashMap<>();

		for (Class<?> injectorClass : injectorClasses) {
			assertInjectorClassValid(injectorClass);

			Set<Method> injectionMethods = new LinkedHashSet<>();
			for (Method injectorMethod : injectorClass.getDeclaredMethods()) {
				if (!injectorMethod.isAnnotationPresent(Injection.class))
					continue;
				assertInjectionMethodValid(injectorMethod);

				injectionMethods.add(injectorMethod);
			}

			injectorsWithInjectionMethods.put(buildInjectorClassInstance(injectorClass),
					injectionMethods);
		}

		return injectorsWithInjectionMethods;
	}

	private static Object buildInjectorClassInstance(Class<?> injectorClass) {
		try {
			injectorClass.getConstructor().setAccessible(true);
			return injectorClass.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new UnexpectedException("Injection methods couldn't be extracted!", e);
		}
	}

	private static void assertInjectorClassValid(Class<?> injectorClass) {
		if (injectorClass.isInterface()) {
			throw new InvalidDataException(
					"An interface cannot be an injector: " + injectorClass.getName());
		}
		if (Modifier.isAbstract(injectorClass.getModifiers())) {
			throw new InvalidDataException(
					"An abstract class cannot be an injector: " + injectorClass.getName());
		}
		if (injectorClass.isMemberClass()) {
			throw new InvalidDataException(
					"A member class cannot be an injector: " + injectorClass.getName());
		}
		if (injectorClass.isLocalClass()) {
			throw new InvalidDataException(
					"A local class cannot be an injector: " + injectorClass.getName());
		}
		if (injectorClass.isAnonymousClass()) {
			throw new InvalidDataException(
					"An anonymous class cannot be an injector: " + injectorClass.getName());
		}

		Constructor<?>[] ctors = injectorClass.getDeclaredConstructors();
		if (ctors.length != 1) {
			throw new InvalidDataException(
					"An injector class can only have default constructor: " + injectorClass.getName());
		}
		if (ctors[0].getParameterCount() != 0) {
			throw new InvalidDataException(
					"An injector class can only have default constructor: " + injectorClass.getName());
		}
	}

	private static void assertInjectionMethodValid(Method injectionMethod) {
		if (Modifier.isPrivate(injectionMethod.getModifiers())) {
			throw new InvalidDataException(
					"An injection method cannot be private: " + injectionMethod.getName());
		}
		if (Modifier.isProtected(injectionMethod.getModifiers())) {
			throw new InvalidDataException(
					"An injection method cannot be protected: " + injectionMethod.getName());
		}
		if (Modifier.isStatic(injectionMethod.getModifiers())) {
			throw new InvalidDataException(
					"An injection method cannot be static: " + injectionMethod.getName());
		}
		if (injectionMethod.getReturnType().isPrimitive()) {
			throw new InvalidDataException(
					"An injection method cannot have primitive return type: " + injectionMethod.getName());
		}
		if (Stream.of(injectionMethod.getParameters()).anyMatch(p -> p.getType().isPrimitive())) {
			throw new InvalidDataException("An injection method cannot have primitive parameters types: "
					+ injectionMethod.getName());
		}
	}

}
