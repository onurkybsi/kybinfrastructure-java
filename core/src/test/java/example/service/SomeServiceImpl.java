package example.service;

import org.kybinfrastructure.ioc.Impl;
import example.service.sub.AnotherService;

@Impl
class SomeServiceImpl implements SomeService {

  private final AnotherService anotherService;

  SomeServiceImpl(AnotherService anotherService) {
    this.anotherService = anotherService;
  }

  @Override
  public void someService() {
    anotherService.anotherService();
  }

}
