package org.kybinfrastructure.ioc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DependencyResolverImpl implements DependencyResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(KybContainer.class);

	@Override
	public Set<ManagedClass> resolve(Map<Object, Set<Method>> injectorsWithInjectionMethods) {
		Map<Method, Object> injectionMethodsWithInjectors =
				buildInjectionMethodsWithInjectors(injectorsWithInjectionMethods);

		injectionMethodsWithInjectors = injectionMethodsWithInjectors.entrySet().stream()
				.sorted(
						(mwi1, mwi2) -> mwi1.getKey().getParameterCount() - mwi2.getKey().getParameterCount())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));

		// TODO: ...
		return null;
	}

	private static Map<Method, Object> buildInjectionMethodsWithInjectors(
			Map<Object, Set<Method>> injectorsWithInjectionMethods) {
		Map<Method, Object> injectionMethodsWithInjectors = new HashMap<>();

		for (var injectorWithInjectionMethods : injectorsWithInjectionMethods.entrySet()) {
			for (Method injectionMethod : injectorWithInjectionMethods.getValue()) {
				injectionMethodsWithInjectors.put(injectionMethod, injectorWithInjectionMethods.getKey());
			}
		}

		return injectionMethodsWithInjectors;
	}

}
