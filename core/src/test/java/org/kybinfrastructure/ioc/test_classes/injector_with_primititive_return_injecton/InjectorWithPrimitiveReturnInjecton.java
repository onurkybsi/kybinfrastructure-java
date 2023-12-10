package org.kybinfrastructure.ioc.test_classes.injector_with_primititive_return_injecton;

import org.kybinfrastructure.ioc.Injection;
import org.kybinfrastructure.ioc.Injector;

@Injector
public class InjectorWithPrimitiveReturnInjecton {

  @Injection
  int someService() {
    return 0;
  }

}
