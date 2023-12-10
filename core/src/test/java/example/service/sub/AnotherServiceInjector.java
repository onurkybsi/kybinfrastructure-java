package example.service.sub;

import org.kybinfrastructure.ioc.Injection;
import org.kybinfrastructure.ioc.Injector;

@Injector
public class AnotherServiceInjector {

  @Injection
  AnotherServiceImpl anotherServiceImpl() {
    return new AnotherServiceImpl();
  }

}
