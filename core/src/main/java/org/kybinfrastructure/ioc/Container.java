package org.kybinfrastructure.ioc;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.kybinfrastructure.exception.UnexpectedException;
import org.kybinfrastructure.utils.validation.Assertions;

final class Container {

	private final Map<Class<?>, ManagedClass> managedClasses;
	private final HashMap<Class<?>, Object> managedInstances = new HashMap<>();

	Container(Set<ManagedClass> managedClasses) {
		LinkedHashMap<Class<?>, ManagedClass> managedClassesToInitialize = new LinkedHashMap<>();
		managedClasses.stream()
				.sorted((m1, m2) -> m1.getCtr().getParameterCount() - m2.getCtr().getParameterCount())
				.forEach(m -> managedClassesToInitialize.put(m.getClazz(), m));

		this.managedClasses = managedClassesToInitialize;
	}

	Container init() {
		try {
			for (Map.Entry<Class<?>, ManagedClass> managedClass : managedClasses.entrySet()) {
				Constructor<?> ctr = managedClass.getValue().getCtr();
				if (ctr.getParameterCount() == 0) {
					managedInstances.put(managedClass.getKey(),
							managedClass.getKey().cast(ctr.newInstance()));
					continue;
				}

				Object[] ctrParams = exractCtrParams(ctr);
				managedInstances.put(managedClass.getKey(),
						managedClass.getKey().cast(ctr.newInstance(ctrParams)));
			}

			return this;
		} catch (Exception e) {
			throw new UnexpectedException("Initialization is not successful!", e);
		}
	}

	<T> T get(Class<T> classInstance) {
		Assertions.notNull(classInstance, "classInstance cannot be null!");

		Object instance = findManagedInstance(classInstance);
		if (instance == null) {
			return null;
		}

		return classInstance.cast(instance);
	}

	Set<Class<?>> getManagedClasses() {
		return managedClasses.keySet();
	}

	private Object[] exractCtrParams(Constructor<?> ctr) {
		Class<?>[] parameterTypes = ctr.getParameterTypes();

		Object[] parameters = new Object[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameters[i] = managedInstances.get(parameterTypes[i]);
		}

		return parameters;
	}

	private <T> Object findManagedInstance(Class<T> assignableTypeOfInstance) {
		return managedClasses.keySet().stream().filter(assignableTypeOfInstance::isAssignableFrom)
				.findFirst().map(managedInstances::get).orElse(null);
	}

}
