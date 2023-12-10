package org.kybinfrastructure.ioc.test_classes.multiple_ctor_injector;

import org.kybinfrastructure.ioc.Injector;

@Injector
public class MultipleCtorInjector {

  MultipleCtorInjector() {}

  MultipleCtorInjector(String someValue) {}

}
