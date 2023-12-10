package org.kybinfrastructure.ioc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import example.service.SomeService;

class KybContainerBuilderTest {

  @Test
  void build_Builds_Fresh_KybContainer_With_Set_Properties() {
    // given
    Class<SomeService> rootClass = SomeService.class;
    KybContainerBuilder builder = new KybContainerBuilder(rootClass);

    // when
    KybContainer actualResult = builder.build();

    // then
    assertNotNull(actualResult);
  }

}
