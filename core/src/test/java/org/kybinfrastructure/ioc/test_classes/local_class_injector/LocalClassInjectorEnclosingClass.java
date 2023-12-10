package org.kybinfrastructure.ioc.test_classes.local_class_injector;

import org.kybinfrastructure.ioc.Injector;

public class LocalClassInjectorEnclosingClass {

  void localClassEnclosing() {
    @Injector
    class LocalClassInjector {


    }
  }

}
