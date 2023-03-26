package org.kybinfrastructure.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import example.service.SomeService;
import example.service.sub.AnotherService;

/**
 * Unit tests for the component {@link ClassUtils}
 */
class ClassUtilsTest {

  class SomeMemberClass {
  }

  @Test
  void resolveClassDirectoryPath_ThrowsIllegalArgumentException_WhenGivenClazzIsNull() {
    // given
    Class<?> clazz = null;

    // when
    IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class,
        () -> ClassUtils.resolveClassDirectoryPath(clazz));

    // then
    assertEquals("clazz cannot be null!", thrownException.getMessage());
  }

  @ParameterizedTest
  @MethodSource("provideClassesForResolveClassDirectoryPath")
  void resolveClassDirectoryPath_ReturnsClassFullFilePath(Class<?> clazz, String expectedResult) {
    // when
    String actualResult = ClassUtils.resolveClassDirectoryPath(clazz);

    // then
    assertEquals(expectedResult, actualResult);
  }

  private static Stream<Arguments> provideClassesForResolveClassDirectoryPath() {
    class SomeLocalClass {
    }
    String testClassesDir = System.getProperty("user.dir") + "/target/test-classes/";
    return Stream.of(Arguments.of(SomeService.class, testClassesDir + "example/service"),
        Arguments.of(AnotherService.class, testClassesDir + "example/service/sub"),
        Arguments.of(SomeMemberClass.class, testClassesDir + "org/kybinfrastructure/utils"),
        Arguments.of(SomeLocalClass.class, testClassesDir + "org/kybinfrastructure/utils"));
  }

}
