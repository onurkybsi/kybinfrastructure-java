package org.kybinfrastructure.ioc.initializer;

/**
 * Represents the API which initializes(creates) new instances of loaded classes
 */
public interface Initializer {

	/**
	 * Initializes a new instance of the given class instance with the given configuration.
	 * 
	 * @param <T> type of initialized instance
	 * @param classInstance class instance of the instance to initialize
	 * @return initialized instance
	 */
	<T> T init(Class<T> classInstance, InitializationConfig<T> config);

}
