package example.service;

import org.kybinfrastructure.ioc.Injector;
import java.util.List;
import example.service.sub.AnotherService;

class ServiceInjector implements Injector {

  private ServiceInjector() {}

  @Override
  public Iterable<Class<?>> inject() {
    return List.of(AnotherService.class);
  }

}
