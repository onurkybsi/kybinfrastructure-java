package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.InvalidDataException;
import org.kybinfrastructure.exception.NotFoundException;
import org.kybinfrastructure.exception.UnexpectedException;
import org.kybinfrastructure.utils.validation.Assertions;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Component which handles the logic of {@link KybContainer} related with initializing the managed
 * class and serving them
 * 
 * @author Onur Kayabasi (o.kayabasi@outlook.com)
 */
final class Container {

	private final Map<Class<?>, ManagedClass> managedClasses;
	private final HashMap<Class<?>, Object> instances = new HashMap<>();

	private Container(Map<Class<?>, ManagedClass> managedClasses) {
		this.managedClasses = managedClasses;
	}

	static Container build(Set<ManagedClass> managedClasses) {
		Assertions.notEmpty(managedClasses, "managedClasses cannot be null!");
		assertWheterAllManagedClassesAreInitiable(managedClasses);

		LinkedHashMap<Class<?>, ManagedClass> managedClassesToInitialize = new LinkedHashMap<>();
		managedClasses.stream()
				.sorted((m1, m2) -> m1.getCtr().getParameterCount() - m2.getCtr().getParameterCount())
				.forEach(m -> managedClassesToInitialize.put(m.getClazz(), m));

		return new Container(managedClassesToInitialize);
	}

	void init() {
		try {
			for (Map.Entry<Class<?>, ManagedClass> managedClass : managedClasses.entrySet()) {
				Constructor<?> ctr = managedClass.getValue().getCtr();
				if (ctr.getParameterCount() == 0) {
					instances.put(managedClass.getKey(), managedClass.getKey().cast(ctr.newInstance()));
					continue;
				}

				Object[] constructorParameters = getConstructorParameters(ctr);
				instances.put(managedClass.getKey(),
						managedClass.getKey().cast(ctr.newInstance(constructorParameters)));
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new UnexpectedException("Initialization is not successful!", e);
		}
	}

	<T> T getImpl(Class<T> classInstance) {
		Assertions.notNull(classInstance, "classInstance cannot be null!");

		Object instance = findManagedInstance(classInstance);
		if (instance == null) {
			throw new NotFoundException(
					"No implementation found by the given class instance: " + classInstance.getName());
		}

		if (classInstance.isInstance(instance) || classInstance.isAssignableFrom(instance.getClass())) {
			return classInstance.cast(instance);
		}

		throw new NotFoundException(
				"Initiated instance couldn't be cast to the actual type: " + classInstance.getSimpleName());
	}

	private static void assertWheterAllManagedClassesAreInitiable(Set<ManagedClass> managedClasses) {
		for (ManagedClass managedClass : managedClasses) {
			Class<?>[] constructorParameterTypes = managedClass.getCtrParams();
			for (Class<?> constructorParameterType : constructorParameterTypes) {
				if (managedClasses.stream().noneMatch(m -> m.getClazz().equals(constructorParameterType))) {
					throw new InvalidDataException(
							"Managed class has a nonmanaged class constructor parameter: %s -> %s",
							managedClass.getClazz().getSimpleName(), constructorParameterType.getSimpleName());
				}
			}
		}
	}

	private Object[] getConstructorParameters(Constructor<?> ctor) {
		Class<?>[] parameterTypes = ctor.getParameterTypes();

		Object[] parameters = new Object[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameters[i] = instances.get(parameterTypes[i]);
		}

		return parameters;
	}

	private <T> Object findManagedInstance(Class<T> assignableTypeOfInstance) {
		Object instance = instances.get(assignableTypeOfInstance);
		if (instance != null) {
			return instance;
		}

		return managedClasses.keySet().stream()
				.filter(
						m -> Arrays.stream(m.getInterfaces()).anyMatch(i -> i.equals(assignableTypeOfInstance))
								|| m.getSuperclass().equals(assignableTypeOfInstance))
				.findFirst().map(instances::get).orElse(null);
	}

}
