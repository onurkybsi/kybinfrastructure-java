package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.InvalidDataException;
import org.kybinfrastructure.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

final class DependencyResolverImpl implements DependencyResolver {

	private static Logger LOGGER = LoggerFactory.getLogger(KybContainer.class);

	@Override
	public Set<ManagedClass> resolve(Set<Class<?>> classesToManage) {
		Set<ManagedClass> managedClasses = initManagedClasses(classesToManage);
		assertNoInterdependency(managedClasses);
		return managedClasses;
	}

	@SuppressWarnings({"java:S3011"})
	private static Set<ManagedClass> initManagedClasses(Set<Class<?>> classesToManage) {
		Set<ManagedClass> initialized = new HashSet<>();

		for (Class<?> classToManage : classesToManage) {
			if (classToManage.getDeclaredConstructors().length != 1) {
				throw new InvalidDataException(
						"Managed classes should have 'only' 1 constructor(%s has %s)", classToManage.getName(),
						classToManage.getConstructors().length);
			}
			Constructor<?> ctr = classToManage.getDeclaredConstructors()[0];
			ctr.setAccessible(true);

			for (Class<?> parameterType : ctr.getParameterTypes()) {
				if (classesToManage.stream().noneMatch(parameterType::isAssignableFrom)) {
					throw new InvalidDataException(
							"Managed class has a nonmanaged class as a constructor parameter: %s has %s",
							classToManage.getName(), parameterType.getName());
				}
			}

			ManagedClass managedClass = new ManagedClass(classToManage, ctr);
			LOGGER.debug("{} is being managed...", managedClass);
			initialized.add(managedClass);
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
						managedClasses.stream().filter(c -> parameterType.isAssignableFrom(c.getClazz()))
								.findFirst().map(c -> c.getCtrParams()).orElseThrow(() -> new UnexpectedException(
										"%s constructor types couldn't be extracted!", parameterType.getName()));
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
