package org.kybinfrastructure.ioc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DependencyResolverImpl implements DependencyResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(KybContainer.class);

	@Override
	public Set<ManagedClass> resolve(Map<Object, Set<Method>> injectionMethods) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'resolve'");
	}

	// @Override
	// public Set<ManagedClass> resolve(Set<Class<?>> classesToManage) {
	// Set<ManagedClass> managedClasses = initManagedClasses(classesToManage);
	// assertNoInterdependency(managedClasses);
	// return managedClasses;
	// }

	// private static Set<ManagedClass> initManagedClasses(Set<Class<?>> classesToManage) {
	// Set<ManagedClass> initialized = new HashSet<>();

	// for (Class<?> classToManage : classesToManage) {
	// Constructor<?> ctr = extractConstructorToUse(classToManage);
	// assertNoNonManagedClassAsCtrParam(ctr, classesToManage);

	// ManagedClass managedClass = new ManagedClass(classToManage, ctr);
	// LOGGER.debug("{} is being managed...", managedClass);
	// initialized.add(managedClass);
	// }

	// return initialized;
	// }

	// @SuppressWarnings({"java:S3011"})
	// private static Constructor<?> extractConstructorToUse(Class<?> classToManage) {
	// if (classToManage.getDeclaredConstructors().length != 1) {
	// throw new InvalidDataException("Managed classes should have 'only' 1 constructor(%s has %s)",
	// classToManage.getName(), classToManage.getDeclaredConstructors().length);
	// }

	// Constructor<?> ctr = classToManage.getDeclaredConstructors()[0];
	// ctr.setAccessible(true);
	// return ctr;
	// }

	// private static void assertNoNonManagedClassAsCtrParam(Constructor<?> ctr,
	// Set<Class<?>> classesToManage) {
	// for (Class<?> paramType : ctr.getParameterTypes()) {
	// if (classesToManage.stream().noneMatch(paramType::isAssignableFrom)) {
	// throw new InvalidDataException(
	// "Managed class has a nonmanaged class as a constructor parameter: %s has %s",
	// ctr.getDeclaringClass().getName(), paramType.getName());
	// }
	// }
	// }

	// private static void assertNoInterdependency(Set<ManagedClass> managedClasses) {
	// for (ManagedClass managedClass : managedClasses) {
	// if (managedClass.getCtrParams().length == 0) {
	// continue;
	// }

	// for (Class<?> paramType : managedClass.getCtr().getParameterTypes()) {
	// Class<?>[] ctrTypesOfParam =
	// managedClasses.stream().filter(c -> paramType.isAssignableFrom(c.getClazz()))
	// .findFirst().map(c -> c.getCtrParams()).orElseThrow(() -> new UnexpectedException(
	// "%s constructor types couldn't be extracted!", paramType.getName()));

	// for (Class<?> ctrTypeOfParam : ctrTypesOfParam) {
	// if (ctrTypeOfParam.equals(managedClass.getClazz())) {
	// throw new InvalidDataException("Interdependency has been detected between %s & %s",
	// managedClass.getClazz().getName(), paramType.getName());
	// }
	// }
	// }
	// }
	// }

}
