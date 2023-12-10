package org.kybinfrastructure.ioc.test_classes.injector_with_protected_injecton;

import org.kybinfrastructure.ioc.Injection;
import org.kybinfrastructure.ioc.Injector;
import example.service.SomeService;

@Injector
public class InjectorWithProtectedInjecton {

  @Injection
  protected SomeService someService() {
    return new SomeService() {
      @Override
      public void someService() {
        throw new UnsupportedOperationException("Unimplemented method 'someService'");
      }
    };
  }

}
