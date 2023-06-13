package org.kybinfrastructure.ioc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import example.service.SomeService;

class KybContainerTest {

  @Test
  void testGetImpl() {
    // given
    KybContainer container = KybContainerBuilder.build(SomeService.class);

    // when
    SomeService actualResult = container.getImpl(SomeService.class);

    // then
    assertNotNull(actualResult);
  }

}
