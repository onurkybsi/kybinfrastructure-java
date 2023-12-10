package example.service;

import org.kybinfrastructure.ioc.Injection;
import org.kybinfrastructure.ioc.Injector;
import example.service.sub.AnotherService;

@Injector
// public is not required, this was made public for testing purposes
public class ServiceInjector {

  @Injection
  SomeServiceImpl someServiceImpl(AnotherService anotherService) {
    return new SomeServiceImpl(anotherService);
  }

  void someNonInjectionMethod() {}

}
