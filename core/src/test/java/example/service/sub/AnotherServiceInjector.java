package example.service.sub;

import org.kybinfrastructure.ioc.Injection;
import org.kybinfrastructure.ioc.Injector;

@Injector
class AnotherServiceInjector {

  private AnotherServiceInjector() {}

  @Injection
  AnotherServiceImpl anotherServiceImpl() {
    return new AnotherServiceImpl();
  }

}
