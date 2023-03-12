package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import org.kybinfrastructure.utils.validation.Assertions;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class InitializerImpl implements Initializer {

	private final Map<Class<?>, ManagedClass> managedClasses;

	private InitializerImpl(Map<Class<?>, ManagedClass> managedClasses) {
		this.managedClasses = managedClasses;
	}

	public static InitializerImpl of(Set<ManagedClass> managedClasses) {
		Assertions.notEmpty(managedClasses, "managedClasses cannot be null!");

		LinkedHashMap<Class<?>, ManagedClass> managedClassesToInitialize = new LinkedHashMap<>();
		managedClasses.stream()
				.sorted((m1, m2) -> m1.getCtr().getParameterCount() - m2.getCtr().getParameterCount())
				.forEach(m -> managedClassesToInitialize.put(m.getClazz(), m));

		return new InitializerImpl(managedClassesToInitialize);
	}

	@Override
	public <T> T init(Class<T> classInstance, InitializationConfig config) {
		try {
			ManagedClass classToInit = this.managedClasses.get(classInstance);
			Constructor<?> ctor = classToInit.getCtr();
			Object initiated = ctor.newInstance();
			return classInstance.cast(initiated);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SecurityException e) {
			throw new KybInfrastructureException("Initialization is not successful & classInstance: %s",
					classInstance.getName(), e);
		}
	}

}
