package org.kybinfrastructure.ioc.initializer;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class InitializerImpl implements Initializer {

	@Override
	public <T> T init(Class<T> classInstance, InitializationConfig<T> config) {
		try {
			Constructor<?> ctor = classInstance.getDeclaredConstructor();
			Object initiated = ctor.newInstance();
			return classInstance.cast(initiated);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new KybInfrastructureException("Initialization is not successful & classInstance: %s",
					classInstance.getName(), e);
		}
	}

}
