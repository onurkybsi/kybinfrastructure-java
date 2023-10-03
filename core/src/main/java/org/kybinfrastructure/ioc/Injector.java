package org.kybinfrastructure.ioc;

/**
 * Represents the components which injects dependencies into the {@link KybContainer} to be built.
 * 
 * @author Onur Kayabasi (o.kayabasi@outlook.com)
 */
public interface Injector {

  /**
   * Returns the class instances to be injected into the {@link KybContainer}.
   *
   * @return the class instances to be injected
   */
  Iterable<Class<?>> inject();

}
