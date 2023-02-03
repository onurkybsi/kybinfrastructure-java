package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import org.kybinfrastructure.utils.validation.Assertions;
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

	private Set<ManagedClass> initManagedClasses(Set<Class<? extends Object>> classesToManage) {
		Set<ManagedClass> initialized = new HashSet<>();

		for (Class<?> classToManage : classesToManage) {
			if (classToManage.getConstructors().length != 1) {
				throw new KybInfrastructureException(
						"Managed class cannot have more than 1 constructor: %s", classToManage);
			}
			initialized.add(new ManagedClass(classToManage, classToManage.getConstructors()[0]));
		}

		return initialized;
	}

	private void assertNoInterdependency(Set<ManagedClass> managedClasses) {}

}
