package example.service;

import example.service.sub.AnotherService;

public class SomeServiceImpl implements SomeService {

  private final AnotherService anotherService;

  SomeServiceImpl(AnotherService anotherService) {
    this.anotherService = anotherService;
  }

  @Override
  public void someService() {
    anotherService.anotherService();
  }

}
