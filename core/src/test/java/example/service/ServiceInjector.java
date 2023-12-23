package example.service;

import org.kybinfrastructure.ioc.Injection;
import org.kybinfrastructure.ioc.Injector;
import example.service.sub.AnotherService;

// Public modifier is not required, this class was made public for testing purposes.
@Injector
public class ServiceInjector {

  @Injection
  SomeServiceImpl someServiceImpl(AnotherService anotherService) {
    return new SomeServiceImpl(anotherService);
  }

  void someNonInjectionMethod() {}

}
