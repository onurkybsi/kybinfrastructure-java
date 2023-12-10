package org.kybinfrastructure.ioc;

import static org.kybinfrastructure.ioc.InjectorValidator.assertInjectionMethodValid;
import static org.kybinfrastructure.ioc.InjectorValidator.assertInjectorClassValid;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import org.kybinfrastructure.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;

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

	private final Container container;

	KybContainer(Class<?> rootClass) {
		LOGGER.debug("KybContainer is being built with root class: {}", rootClass);

		Set<Class<?>> injectorClasses = SCANNER.scan(rootClass);
		Map<Method, Object> injectionMethodsWithInjectors =
				extractInjectionMethodsWithInjectors(injectorClasses);
		Set<ManagedClass> managedClasses = buildManagedClasses(injectionMethodsWithInjectors);
		container = new Container(managedClasses);
		container.init();

		LOGGER.debug("KybContainer was built!");
	}

	// #region Public API
	/**
	 * Returns the builder for {@link KybContainer}.
	 *
	 * @param rootClass root class for the {@link Injector} classes.
	 * @return builder for {@link KybContainer}
	 * @throws IllegalArgumentException when given {@code rootClass} is null
	 */
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
	 * @return <i>KybContainer</i> initialized instance, {@code Optional.empty} when no initialized
	 *         instance found by the given type
	 */
	public <T> Optional<T> get(Class<T> classInstance) {
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

	private static Map<Method, Object> extractInjectionMethodsWithInjectors(
			Set<Class<?>> injectorClasses) {
		Map<Method, Object> injectionMethodsWithInjectors = new LinkedHashMap<>();

		for (Class<?> injectorClass : injectorClasses) {
			assertInjectorClassValid(injectorClass);

			Object injectorClassInstance = buildInjectorClassInstance(injectorClass);
			for (Method injectorMethod : injectorClass.getDeclaredMethods()) {
				if (!injectorMethod.isAnnotationPresent(Injection.class))
					continue;
				assertInjectionMethodValid(injectorMethod);

				injectionMethodsWithInjectors.put(injectorMethod, injectorClassInstance);
			}
		}

		return injectionMethodsWithInjectors;
	}

	private static Object buildInjectorClassInstance(Class<?> injectorClass) {
		try {
			Constructor<?> ctr = injectorClass.getDeclaredConstructor();
			ctr.setAccessible(true);
			return ctr.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new UnexpectedException("Injection methods couldn't be extracted!", e);
		}
	}

	private static Set<ManagedClass> buildManagedClasses(
			Map<Method, Object> injectionMethodsWithInjectors) {
		Set<ManagedClass> classesToManage = new HashSet<>();

		for (Entry<Method, Object> injectionMethodWithInjector : injectionMethodsWithInjectors
				.entrySet()) {
			var classToManage = new ManagedClass(injectionMethodWithInjector.getKey().getReturnType(),
					injectionMethodWithInjector.getValue(), injectionMethodWithInjector.getKey());
			classesToManage.add(classToManage);
			LOGGER.trace("{} added to managed classes!", classToManage);
		}

		return classesToManage;
	}

}
