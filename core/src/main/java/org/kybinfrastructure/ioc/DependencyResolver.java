package org.kybinfrastructure.ioc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

interface DependencyResolver {

	Set<ManagedClass> resolve(Map<Object, Set<Method>> injectorsWithInjectionMethods);

}
