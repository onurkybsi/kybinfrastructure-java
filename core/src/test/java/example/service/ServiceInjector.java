package example.service;

import org.kybinfrastructure.ioc.Injection;
import org.kybinfrastructure.ioc.Injector;
import example.service.sub.AnotherService;

@Injector
class ServiceInjector {

  private ServiceInjector() {}

  @Injection
  SomeServiceImpl someServiceImpl(AnotherService anotherService) {
    return new SomeServiceImpl(anotherService);
  }

  void someNonInjectionMethod() {}

}
