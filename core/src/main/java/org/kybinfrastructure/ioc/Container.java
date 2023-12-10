package org.kybinfrastructure.ioc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.kybinfrastructure.exception.InvalidDataException;
import org.kybinfrastructure.exception.UnexpectedException;
import org.kybinfrastructure.utils.validation.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class Container {

	private static final Logger LOGGER = LoggerFactory.getLogger(KybContainer.class);

	private final Map<Class<?>, ManagedClass> managedClasses;
	private final HashMap<Class<?>, Object> managedInstances = new HashMap<>();

	Container(Set<ManagedClass> managedClasses) {
		LinkedHashMap<Class<?>, ManagedClass> _managedClasses = new LinkedHashMap<>();
		managedClasses.stream()
				.sorted((c1, c2) -> c1.getFactoryMethod().getParameterCount()
						- c2.getFactoryMethod().getParameterCount())
				.forEach(m -> _managedClasses.put(m.getClazz(),
						new ManagedClass(m.getClazz(), m.getInjectorInstance(), m.getFactoryMethod())));

		this.managedClasses = _managedClasses;
	}

	Container init() {
		LOGGER.trace("Container is being initialized {}",
				Arrays.toString(managedClasses.values().toArray()));

		try {
			for (Map.Entry<Class<?>, ManagedClass> managedClass : managedClasses.entrySet()) {
				Object injectorInstance = managedClass.getValue().getInjectorInstance();
				Method factoryMethod = managedClass.getValue().getFactoryMethod();
				factoryMethod.setAccessible(true);

				if (factoryMethod.getParameterCount() == 0) {
					managedInstances.put(managedClass.getKey(),
							managedClass.getKey().cast(factoryMethod.invoke(injectorInstance)));
					continue;
				} else {
					Object[] factoryMethodParams = exractFactoryMethodParams(factoryMethod);

					managedInstances.put(managedClass.getKey(), managedClass.getKey()
							.cast(factoryMethod.invoke(injectorInstance, factoryMethodParams)));
				}
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new UnexpectedException("Initialization is not successful!", e);
		}

		LOGGER.trace("Container was initialized with {} instances!", managedInstances.size());
		return this;
	}

	<T> Optional<T> get(Class<T> classInstance) {
		Assertions.notNull(classInstance, "classInstance cannot be null!");
		return Optional.ofNullable(findManagedInstance(classInstance)).map(classInstance::cast);
	}

	Set<Class<?>> getManagedClasses() {
		return Set.of(managedClasses.keySet().toArray(Class[]::new));
	}

	private Object[] exractFactoryMethodParams(Method factoryMethod) {
		Class<?>[] parameterTypes = factoryMethod.getParameterTypes();

		Object[] parameters = new Object[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameters[i] = findManagedInstance(parameterTypes[i]);

			if (parameters[i] == null) {
				// TODO: Take this into KybContainer. We don't want invalid data at this point.
				throw new InvalidDataException(
						"%s has non-managed class parameter!".formatted(factoryMethod));
			}
		}

		return parameters;
	}

	private <T> Object findManagedInstance(Class<T> assignableTypeOfInstance) {
		return managedClasses.keySet().stream().filter(assignableTypeOfInstance::isAssignableFrom)
				.findFirst().map(managedInstances::get).orElse(null);
	}

}
