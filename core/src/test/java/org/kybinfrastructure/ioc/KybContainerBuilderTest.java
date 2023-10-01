package org.kybinfrastructure.ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import example.service.SomeService;
import example.service.sub.AnotherService;

class KybContainerBuilderTest {

  @Test
  void kybContainerBuilder_Throws_IllegalArgumentException_When_GivenRootClass_IsNull() {
    // given
    Class<?> rootClass = null;

    // when
    IllegalArgumentException thrownException =
        assertThrows(IllegalArgumentException.class, () -> new KybContainerBuilder(rootClass));

    // then
    assertEquals("rootClass cannot be null!", thrownException.getMessage());
  }

  @Test
  void build_Builds_Fresh_KybContainer_With_Given_Properties() {
    // given
    Class<SomeService> rootClass = SomeService.class;
    KybContainerBuilder builder = new KybContainerBuilder(rootClass);

    // when
    KybContainer actualResult = builder.build();

    // then
    assertNotNull(actualResult);
    var managedClasses = actualResult.getManagedClasses();
    assertEquals(2, managedClasses.size());
    assertTrue(managedClasses.stream().anyMatch(mc -> SomeService.class.isAssignableFrom(mc)));
    assertTrue(managedClasses.stream().anyMatch(mc -> AnotherService.class.isAssignableFrom(mc)));
  }

}
