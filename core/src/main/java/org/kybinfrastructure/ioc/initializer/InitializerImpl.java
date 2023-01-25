package org.kybinfrastructure.ioc.initializer;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class InitializerImpl implements Initializer {

	@Override
	public <T> T init(Class<T> classInstance) {
		try {
			// TODO:
			// 1 - How can we decide to the constructor which is used to init ?
			// 2 - How can we manage scopes ?
			Constructor<?> ctor = classInstance.getConstructors()[0];
			Object initiated;
			initiated = ctor.newInstance();
			return classInstance.isInstance(initiated) ? classInstance.cast(initiated) : null;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new KybInfrastructureException("Initialization is not successful & classInstance: %s",
					classInstance.getName(), e);
		}
	}

	@Override
	public <T> T init(Class<T> classInstance, InitializationConfig config) {
		// TODO: It's going to be implemented
		return null;
	}

}
