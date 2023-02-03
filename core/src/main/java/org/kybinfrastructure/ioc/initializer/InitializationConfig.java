package org.kybinfrastructure.ioc.initializer;

import java.lang.reflect.Constructor;
import java.util.function.Predicate;

public class InitializationConfig<T> {

	private final Predicate<Constructor<T>> filter;

	private InitializationConfig(Predicate<Constructor<T>> filter) {
		this.filter = filter;
	}

	public static <T> InitializationConfig<T> of(Predicate<Constructor<T>> filter) {
		return new InitializationConfig<>(filter);
	}

	public Predicate<Constructor<T>> getFilter() {
		return filter;
	}

}
