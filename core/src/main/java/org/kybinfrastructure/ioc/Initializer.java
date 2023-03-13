package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import org.kybinfrastructure.utils.validation.Assertions;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

class Initializer {

	private final Map<Class<?>, ManagedClass> managedClasses;
	private final HashMap<Class<?>, Object> instances = new HashMap<>();

	private Initializer(Map<Class<?>, ManagedClass> managedClasses) {
		this.managedClasses = managedClasses;
	}

	static Initializer build(Set<ManagedClass> managedClasses) {
		Assertions.notEmpty(managedClasses, "managedClasses cannot be null!");
		assertWheterAllTypesAreInitiable(managedClasses);

		LinkedHashMap<Class<?>, ManagedClass> managedClassesToInitialize = new LinkedHashMap<>();
		managedClasses.stream()
				.sorted((m1, m2) -> m1.getCtr().getParameterCount() - m2.getCtr().getParameterCount())
				.forEach(m -> managedClassesToInitialize.put(m.getClazz(), m));

		return new Initializer(managedClassesToInitialize);
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
			throw new KybInfrastructureException("Initialization is not successful!", e);
		}
	}

	/**
	 * Returns the <i>KybContainer</i> initialized instance of the given type.
	 *
	 * @param <T> type of the instance
	 * @param classInstance class instance of the type
	 * @return <i>KybContainer</i> initialized instance
	 * @throws KybInfrastructureException if no instance found in the container by given type
	 */
	<T> T getImpl(Class<T> classInstance) {
		Assertions.notNull(classInstance, "classInstance cannot be null!");

		Object instance = instances.get(classInstance);
		if (instance == null) {
			// TODO: Should we use exception type like NoImpFound extends KybInfrastructureException ?
			throw new KybInfrastructureException("No implementation found by the given class instance!");
		}

		if (classInstance.isInstance(instance)) {
			return classInstance.cast(instance);
		}

		throw new KybInfrastructureException("Initiated instance couldn't be cast to the actual type!");
	}

	private static void assertWheterAllTypesAreInitiable(Set<ManagedClass> managedClasses) {
		for (ManagedClass managedClass : managedClasses) {
			Class<?>[] constructorParameterTypes = managedClass.getCtrParams();
			for (Class<?> constructorParameterType : constructorParameterTypes) {
				if (managedClasses.stream().noneMatch(m -> m.getClazz().equals(constructorParameterType))) {
					throw new KybInfrastructureException(
							"Managed classes has not the constructor parameter of %s: %s", managedClass,
							constructorParameterType);
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

}
