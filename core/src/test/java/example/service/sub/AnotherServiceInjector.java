package example.service.sub;

import org.kybinfrastructure.ioc.Injector;

import java.util.List;

class AnotherServiceInjector implements Injector {

  private AnotherServiceInjector() {
  }

  @Override
  public Iterable<Class<?>> inject() {
    return List.of(AnotherServiceImpl.class);
  }

}