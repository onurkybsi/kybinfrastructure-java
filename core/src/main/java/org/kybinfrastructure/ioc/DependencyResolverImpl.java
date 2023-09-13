package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.InvalidDataException;
import org.kybinfrastructure.exception.UnexpectedException;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation for {@link DependencyResolver}
 */
class DependencyResolverImpl implements DependencyResolver {

	@Override
	public Set<ManagedClass> resolve(Set<Class<?>> classesToManage) {
		Set<ManagedClass> managedClasses = initManagedClasses(classesToManage);
		assertNoInterdependency(managedClasses);
		return managedClasses;
	}

	private static Set<ManagedClass> initManagedClasses(Set<Class<?>> classesToManage) {
		Set<ManagedClass> initialized = new HashSet<>();

		for (Class<?> classToManage : classesToManage) {
			if (classToManage.getDeclaredConstructors().length != 1) {
				throw new InvalidDataException("Managed classes should have 'only' 1 constructor(%s has %s)",
						classToManage.getName(), classToManage.getConstructors().length);
			}

			Constructor<?> ctr = classToManage.getDeclaredConstructors()[0];
			for (Class<?> parameterType : ctr.getParameterTypes()) {
				// TODO: Fix this, this doesn't work!
				if (classesToManage.stream().noneMatch(c -> c.isAssignableFrom(parameterType))) {
					throw new InvalidDataException(
							"Managed class has a nonmanaged class constructor parameter: %s -> %s",
							classToManage.getSimpleName(), parameterType.getSimpleName());
				}
			}

			initialized.add(new ManagedClass(classToManage, ctr));
		}

		return initialized;
	}

	private static void assertNoInterdependency(Set<ManagedClass> managedClasses) {
		for (ManagedClass managedClass : managedClasses) {
			if (managedClass.getCtrParams().length == 0) {
				continue;
			}

			for (Class<?> parameterType : managedClass.getCtr().getParameterTypes()) {
				Class<?>[] constructorTypesOfParameter = managedClasses.stream().filter(c -> c.getClazz().equals(parameterType))
						.findFirst()
						.map(c -> c.getCtrParams())
						.orElseThrow(
								() -> new UnexpectedException("%s constructor types couldn't be extracted!", parameterType.getName()));
				for (Class<?> constructorTypeOfParameter : constructorTypesOfParameter) {
					if (constructorTypeOfParameter.equals(managedClass.getClazz())) {
						throw new InvalidDataException("Interdependency has been detected between %s & %s",
								managedClass.getClazz().getName(), parameterType.getName());
					}
				}
			}
		}
	}

}
