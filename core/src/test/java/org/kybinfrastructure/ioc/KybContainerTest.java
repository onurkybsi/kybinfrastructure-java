package org.kybinfrastructure.ioc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import example.service.SomeService;

class KybContainerTest {

  @Test
  void test() {
    KybContainer container = KybContainerBuilder.build(SomeService.class);
    assertNotNull(container.getImpl(SomeService.class));
  }

}
