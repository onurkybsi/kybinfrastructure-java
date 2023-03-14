package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.InvalidDataException;
import org.kybinfrastructure.exception.KybInfrastructureException;
import org.kybinfrastructure.utils.validation.Assertions;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation for {@link DependencyResolver}
 */
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
				throw new KybInfrastructureException(
						"Managed classes can only have 1 constructor(%s has %s)", classToManage.getSimpleName(),
						classToManage.getConstructors().length);
			}

			Constructor<?> ctr = classToManage.getConstructors()[0];
			for (Class<?> parameterType : ctr.getParameterTypes()) {
				if (!classesToManage.contains(parameterType)) {
					throw new KybInfrastructureException(
							"Managed class has a nonmanaged class constructor parameter: %s -> %s",
							classToManage.getSimpleName(), parameterType.getSimpleName());
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

			for (Class<?> parameterType : managedClass.getCtr().getParameterTypes()) {
				Class<?>[] constructorTypesOfParameter =
						managedClasses.stream().filter(c -> c.getClazz().equals(parameterType)).findFirst()
								.map(c -> c.getCtrParams()).orElseThrow();
				for (Class<?> constructorTypeOfParameter : constructorTypesOfParameter) {
					if (constructorTypeOfParameter.equals(managedClass.getClazz())) {
						throw new InvalidDataException("Interdependency has been found between %s & %s",
								managedClass.getClazz().getSimpleName(), parameterType.getSimpleName());
					}
				}
			}
		}
	}

}
