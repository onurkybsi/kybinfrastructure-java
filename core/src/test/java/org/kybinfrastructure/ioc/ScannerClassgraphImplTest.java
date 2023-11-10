package org.kybinfrastructure.ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.kybinfrastructure.exception.UnexpectedException;
import example.service.SomeService;

class ScannerClassgraphImplTest {

  private final ScannerClassgraphImpl underTest = new ScannerClassgraphImpl();

  @Test
  void scan_Returns_Injector_Classes_Under_GivenRootClass_Package() {
    // given
    Class<?> rootClass = SomeService.class;

    // when
    Set<Class<?>> actualResult = underTest.scan(rootClass);

    // then
    assertEquals(2, actualResult.size());
    assertTrue(actualResult.stream()
        .anyMatch(sc -> "example.service.ServiceInjector".equals(sc.getName())));
    assertTrue(actualResult.stream()
        .anyMatch(sc -> "example.service.sub.AnotherServiceInjector".equals(sc.getName())));
  }

  @Test
  void scan_Throws_UnexpectedException_When_Unexpected_Exception_Occurred() {
    // given
    Class<?> rootClass = null;

    // when
    UnexpectedException thrownException =
        assertThrows(UnexpectedException.class, () -> underTest.scan(rootClass));

    // then
    assertEquals("Injector classes couldn't be extracted!", thrownException.getMessage());
    assertEquals(NullPointerException.class, thrownException.getCause().getClass());
  }

}
