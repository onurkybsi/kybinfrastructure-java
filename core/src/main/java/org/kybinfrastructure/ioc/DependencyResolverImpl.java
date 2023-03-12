package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import org.kybinfrastructure.utils.validation.Assertions;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

class DependencyResolverImpl implements DependencyResolver {

	@Override
	public Set<ManagedClass> resolve(Set<Class<?>> classesToManage) {
		Assertions.notEmpty(classesToManage, "classesToManage cannot be empty!");

		final Set<ManagedClass> managedClasses = initManagedClasses(classesToManage);
		assertNoInterdependency(managedClasses);

		return managedClasses;
	}

	private static Set<ManagedClass> initManagedClasses(Set<Class<?>> classesToManage) {
		Set<ManagedClass> initialized = new HashSet<>();

		for (Class<?> classToManage : classesToManage) {
			if (classToManage.getConstructors().length != 1) {
				throw new KybInfrastructureException("Managed class can have only 1 constructor: %s",
						classToManage);
			}

			Constructor<?> ctr = classToManage.getConstructors()[0];
			for (int i = 0; i < ctr.getParameterTypes().length; i++) {
				if (!classesToManage.contains(ctr.getParameterTypes()[i])) {
					throw new KybInfrastructureException(
							"Managed class has a nonmanaged class constructor parameter: %s -> %s", classToManage,
							ctr.getParameterTypes()[i]);
				}
			}

			initialized.add(new ManagedClass(classToManage, classToManage.getConstructors()[0]));
		}

		return initialized;
	}

	private static void assertNoInterdependency(Set<ManagedClass> managedClasses) {
		for (ManagedClass managedClass : managedClasses) {
			if (managedClass.getCtrParams().length == 0) {
				continue;
			}

			for (int i = 0; i < managedClass.getCtr().getParameterTypes().length; i++) {
				Class<?> parameterType = managedClass.getCtr().getParameterTypes()[i];
				Class<?>[] constructorTypesOfParameter =
						managedClasses.stream().filter(c -> c.getClazz().equals(parameterType)).findFirst()
								.map(c -> c.getCtrParams()).orElseThrow();
				for (Class<?> constructorTypeOfParameter : constructorTypesOfParameter) {
					if (constructorTypeOfParameter.equals(managedClass.getClass())) {
						throw new KybInfrastructureException("Interdependency has been found between %s & %s",
								managedClass.getClass(), parameterType);
					}
				}
			}
		}
	}

}
