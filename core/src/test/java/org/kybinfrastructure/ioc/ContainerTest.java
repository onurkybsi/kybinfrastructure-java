package org.kybinfrastructure.ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.kybinfrastructure.exception.InvalidDataException;
import example.service.ServiceInjector;
import example.service.SomeService;
import example.service.SomeServiceImpl;
import example.service.sub.AnotherService;
import example.service.sub.AnotherServiceImpl;
import example.service.sub.AnotherServiceInjector;

class ContainerTest {

  @Test
  @SuppressWarnings({"unchecked"})
  void container_Constructs_ManagedClasses_By_SortingThem_By_FactoryMethodParamNumber()
      throws Exception {
    // given
    var anotherServiceInjectionMethod =
        AnotherServiceInjector.class.getDeclaredMethod("anotherServiceImpl");
    anotherServiceInjectionMethod.setAccessible(true);
    var anotherServiceManagedClass = new ManagedClass(AnotherServiceImpl.class,
        new AnotherServiceInjector(), anotherServiceInjectionMethod);
    var someServiceInjectionMethod =
        ServiceInjector.class.getDeclaredMethod("someServiceImpl", AnotherService.class);
    someServiceInjectionMethod.setAccessible(true);
    var someServiceManagedClass =
        new ManagedClass(SomeServiceImpl.class, new ServiceInjector(), someServiceInjectionMethod);
    Set<ManagedClass> managedClasses = Set.of(someServiceManagedClass, anotherServiceManagedClass);

    // when
    Container underTest = new Container(managedClasses);

    // then
    Field constructedManagedClassesField = Container.class.getDeclaredField("managedClasses");
    constructedManagedClassesField.setAccessible(true);
    var constructedManagedClasses =
        (Map<Class<?>, ManagedClass>) constructedManagedClassesField.get(underTest);
    assertEquals(2, constructedManagedClasses.size());
    var constructedManagedClassesIterator = constructedManagedClasses.keySet().iterator();
    assertEquals(AnotherServiceImpl.class, constructedManagedClassesIterator.next());
    assertEquals(SomeServiceImpl.class, constructedManagedClassesIterator.next());
  }

  @Test
  void init_Initializes_ManagedInstances() throws Exception {
    // given
    var anotherServiceInjectionMethod =
        AnotherServiceInjector.class.getDeclaredMethod("anotherServiceImpl");
    anotherServiceInjectionMethod.setAccessible(true);
    var anotherServiceManagedClass = new ManagedClass(AnotherServiceImpl.class,
        new AnotherServiceInjector(), anotherServiceInjectionMethod);
    var someServiceInjectionMethod =
        ServiceInjector.class.getDeclaredMethod("someServiceImpl", AnotherService.class);
    someServiceInjectionMethod.setAccessible(true);
    var someServiceManagedClass =
        new ManagedClass(SomeServiceImpl.class, new ServiceInjector(), someServiceInjectionMethod);
    Set<ManagedClass> managedClasses = Set.of(someServiceManagedClass, anotherServiceManagedClass);
    Container underTest = new Container(managedClasses);

    // when
    underTest.init();

    // then
    assertEquals(2, underTest.getManagedClasses().size());
    assertNotNull(underTest.get(AnotherService.class).orElseThrow());
    assertNotNull(underTest.get(SomeService.class).orElseThrow());
  }

  @Test
  void init_Throws_InvalidDataException_When_GivenManagedClassesFactoryMethod_Contain_NonManagedClass()
      throws Exception {
    // given
    final class TestManagedClass {
    }
    final class TestNonManagedClass {
    }
    @Injector
    final class TestManagedClassInjector {
      @Injection
      TestManagedClass testManagedClass(TestNonManagedClass nonManagedClass) {
        return new TestManagedClass();
      }
    }
    Container underTest = new Container(Set.of(new ManagedClass(TestManagedClass.class,
        new TestManagedClassInjector(), TestManagedClassInjector.class
            .getDeclaredMethod("testManagedClass", TestNonManagedClass.class))));

    // when
    InvalidDataException thrownException =
        assertThrows(InvalidDataException.class, () -> underTest.init());

    // then
    assertEquals(
        "org.kybinfrastructure.ioc.ContainerTest$1TestManagedClass org.kybinfrastructure.ioc.ContainerTest$1TestManagedClassInjector.testManagedClass(org.kybinfrastructure.ioc.ContainerTest$1TestNonManagedClass) has non-managed class parameter!",
        thrownException.getMessage());
  }

  @Test
  void get_Returns_ManagedInstance_By_GivenClassInstance() throws Exception {
    // given
    var anotherServiceInjectionMethod =
        AnotherServiceInjector.class.getDeclaredMethod("anotherServiceImpl");
    anotherServiceInjectionMethod.setAccessible(true);
    var anotherServiceManagedClass = new ManagedClass(AnotherServiceImpl.class,
        new AnotherServiceInjector(), anotherServiceInjectionMethod);
    Set<ManagedClass> managedClasses = Set.of(anotherServiceManagedClass);
    Container underTest = new Container(managedClasses);
    underTest.init();

    // when
    AnotherService actualResult = underTest.get(AnotherService.class).orElseThrow();

    // then
    assertNotNull(actualResult);
  }

  @Test
  void get_Returns_OptionalEmpty_When_NoManagedInstance_Found_By_GivenClassInstance()
      throws Exception {
    // given
    var anotherServiceInjectionMethod =
        AnotherServiceInjector.class.getDeclaredMethod("anotherServiceImpl");
    anotherServiceInjectionMethod.setAccessible(true);
    var anotherServiceManagedClass = new ManagedClass(AnotherServiceImpl.class,
        new AnotherServiceInjector(), anotherServiceInjectionMethod);
    Set<ManagedClass> managedClasses = Set.of(anotherServiceManagedClass);
    Container underTest = new Container(managedClasses).init();

    // when
    var actualResult = underTest.get(ContainerTest.class);

    // then
    assertTrue(actualResult.isEmpty());
  }

  @Test
  void get_Throws_IllegalArgumentException_When_GivenClassInstance_IsNull() throws Exception {
    // given
    var anotherServiceInjectionMethod =
        AnotherServiceInjector.class.getDeclaredMethod("anotherServiceImpl");
    anotherServiceInjectionMethod.setAccessible(true);
    var anotherServiceManagedClass = new ManagedClass(AnotherServiceImpl.class,
        new AnotherServiceInjector(), anotherServiceInjectionMethod);
    Set<ManagedClass> managedClasses = Set.of(anotherServiceManagedClass);
    Container underTest = new Container(managedClasses).init();

    // when
    IllegalArgumentException thrownException =
        assertThrows(IllegalArgumentException.class, () -> underTest.get(null));

    // then
    assertEquals("classInstance cannot be null!", thrownException.getMessage());
  }

  @Test
  @SuppressWarnings({"unchecked"})
  void getManagedClasses_Returns_DeepCopy_Of_ManagedClassInstances() throws Exception {
    // given
    var anotherServiceInjectionMethod =
        AnotherServiceInjector.class.getDeclaredMethod("anotherServiceImpl");
    anotherServiceInjectionMethod.setAccessible(true);
    var anotherServiceManagedClass = new ManagedClass(AnotherServiceImpl.class,
        new AnotherServiceInjector(), anotherServiceInjectionMethod);
    var someServiceInjectionMethod =
        ServiceInjector.class.getDeclaredMethod("someServiceImpl", AnotherService.class);
    someServiceInjectionMethod.setAccessible(true);
    var someServiceManagedClass =
        new ManagedClass(SomeServiceImpl.class, new ServiceInjector(), someServiceInjectionMethod);
    Set<ManagedClass> managedClasses = Set.of(someServiceManagedClass, anotherServiceManagedClass);
    Container underTest = new Container(managedClasses).init();

    // when
    var actualResult = underTest.getManagedClasses();

    // then
    assertEquals(2, actualResult.size());
    assertTrue(actualResult.stream().anyMatch(mc -> mc.equals(SomeServiceImpl.class)));
    assertTrue(actualResult.stream().anyMatch(mc -> mc.equals(AnotherServiceImpl.class)));
    Field managedClassesField = Container.class.getDeclaredField("managedClasses");
    managedClassesField.setAccessible(true);
    var constructedManagedClasses =
        (Map<Class<?>, ManagedClass>) managedClassesField.get(underTest);
    assertNotSame(constructedManagedClasses.keySet(), actualResult);
  }

}
