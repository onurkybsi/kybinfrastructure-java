package org.kybinfrastructure.ioc.test_classes.injector_with_primititive_param_injecton;

import org.kybinfrastructure.ioc.Injection;
import org.kybinfrastructure.ioc.Injector;
import example.service.SomeService;

@Injector
public class InjectorWithPrimitiveParamInjecton {

  @Injection
  SomeService someService(int someValue) {
    return new SomeService() {
      @Override
      public void someService() {
        throw new UnsupportedOperationException("Unimplemented method 'someService'");
      }
    };
  }

}
