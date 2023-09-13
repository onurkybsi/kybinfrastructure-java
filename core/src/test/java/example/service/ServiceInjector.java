package example.service;

import org.kybinfrastructure.ioc.Injector;

import java.util.List;

class ServiceInjector implements Injector {

  private ServiceInjector() {
  }

  @Override
  public Iterable<Class<?>> inject() {
    return List.of(SomeServiceImpl.class);
  }

}
