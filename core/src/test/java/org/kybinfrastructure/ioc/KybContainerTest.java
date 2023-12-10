package org.kybinfrastructure.ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kybinfrastructure.exception.InvalidDataException;
import org.kybinfrastructure.ioc.test_classes.abstract_injector.AbstractInjector;
import org.kybinfrastructure.ioc.test_classes.ctor_with_params.CtorWithParams;
import org.kybinfrastructure.ioc.test_classes.injector_with_primititive_param_injecton.InjectorWithPrimitiveParamInjecton;
import org.kybinfrastructure.ioc.test_classes.injector_with_primititive_return_injecton.InjectorWithPrimitiveReturnInjecton;
import org.kybinfrastructure.ioc.test_classes.injector_with_private_injecton.InjectorWithPrivateInjecton;
import org.kybinfrastructure.ioc.test_classes.injector_with_protected_injecton.InjectorWithProtectedInjecton;
import org.kybinfrastructure.ioc.test_classes.injector_with_static_injecton.InjectorWithStaticInjecton;
import org.kybinfrastructure.ioc.test_classes.interface_injector.InterfaceInjector;
import org.kybinfrastructure.ioc.test_classes.local_class_injector.LocalClassInjectorEnclosingClass;
import org.kybinfrastructure.ioc.test_classes.member_class_injector.MemberClassInjectorEnclosingClass;
import org.kybinfrastructure.ioc.test_classes.multiple_ctor_injector.MultipleCtorInjector;
import example.service.SomeService;
import example.service.SomeServiceImpl;
import example.service.sub.AnotherServiceImpl;

class KybContainerTest {

  @BeforeEach
  void refresh() {
    Configurator.setAllLevels("", Level.ALL);
  }

  @Test
  void should_Build_IoCContainer_By_GivenRootClass() throws Exception {
    // given
    Class<?> rootClass = SomeService.class;

    // when
    KybContainer container = new KybContainer(rootClass);

    // then
    var someService = container.get(SomeService.class).orElseThrow();
    var anotherService = container.get(AnotherServiceImpl.class).orElseThrow();
    assertNotNull(someService);
    assertEquals(SomeServiceImpl.class, someService.getClass());
    assertNotNull(anotherService);
    assertEquals(AnotherServiceImpl.class, anotherService.getClass());
    var anotherServiceField = SomeServiceImpl.class.getDeclaredField("anotherService");
    anotherServiceField.setAccessible(true);
    assertEquals(anotherService, anotherServiceField.get(someService));
  }

  @Test
  void should_Throw_InvalidDataException_When_ScannedInjector_Is_Interface() {
    // given
    Class<?> rootClass = InterfaceInjector.class;

    // when
    InvalidDataException thrownException =
        assertThrows(InvalidDataException.class, () -> new KybContainer(rootClass));

    // then
    assertEquals(
        "An interface cannot be an injector: org.kybinfrastructure.ioc.test_classes.interface_injector.InterfaceInjector",
        thrownException.getMessage());
  }

  @Test
  void should_Throw_InvalidDataException_When_ScannedInjector_Is_AbstractClass() {
    // given
    Class<?> rootClass = AbstractInjector.class;

    // when
    InvalidDataException thrownException =
        assertThrows(InvalidDataException.class, () -> new KybContainer(rootClass));

    // then
    assertEquals(
        "An abstract class cannot be an injector: org.kybinfrastructure.ioc.test_classes.abstract_injector.AbstractInjector",
        thrownException.getMessage());
  }

  @Test
  void should_Throw_InvalidDataException_When_ScannedInjector_Is_MemberClass() {
    // given
    Class<?> rootClass = MemberClassInjectorEnclosingClass.class;

    // when
    InvalidDataException thrownException =
        assertThrows(InvalidDataException.class, () -> new KybContainer(rootClass));

    // then
    assertEquals(
        "A member class cannot be an injector: org.kybinfrastructure.ioc.test_classes.member_class_injector.MemberClassInjectorEnclosingClass$MemberClassInjector",
        thrownException.getMessage());
  }

  @Test
  void should_Throw_InvalidDataException_When_ScannedInjector_Is_LocalClass() {
    // given
    Class<?> rootClass = LocalClassInjectorEnclosingClass.class;

    // when
    InvalidDataException thrownException =
        assertThrows(InvalidDataException.class, () -> new KybContainer(rootClass));

    // then
    assertEquals(
        "A local class cannot be an injector: org.kybinfrastructure.ioc.test_classes.local_class_injector.LocalClassInjectorEnclosingClass$1LocalClassInjector",
        thrownException.getMessage());
  }

  @Test
  void should_Throw_InvalidDataException_When_ScannedInjector_Has_MultipleConstructors() {
    // given
    Class<?> rootClass = MultipleCtorInjector.class;

    // when
    InvalidDataException thrownException =
        assertThrows(InvalidDataException.class, () -> new KybContainer(rootClass));

    // then
    assertEquals(
        "An injector class can only have default constructor: org.kybinfrastructure.ioc.test_classes.multiple_ctor_injector.MultipleCtorInjector",
        thrownException.getMessage());
  }

  @Test
  void should_Throw_InvalidDataException_When_ScannedInjector_Has_DefaultConstructor_With_Parameters() {
    // given
    Class<?> rootClass = CtorWithParams.class;

    // when
    InvalidDataException thrownException =
        assertThrows(InvalidDataException.class, () -> new KybContainer(rootClass));

    // then
    assertEquals(
        "An injector class can only have default constructor: org.kybinfrastructure.ioc.test_classes.ctor_with_params.CtorWithParams",
        thrownException.getMessage());
  }

  @Test
  void should_Throw_InvalidDataException_When_ScannedInjector_InjectionMethod_Is_Private() {
    // given
    Class<?> rootClass = InjectorWithPrivateInjecton.class;

    // when
    InvalidDataException thrownException =
        assertThrows(InvalidDataException.class, () -> new KybContainer(rootClass));

    // then
    assertEquals(
        "An injection method cannot be private: class org.kybinfrastructure.ioc.test_classes.injector_with_private_injecton.InjectorWithPrivateInjecton.someService",
        thrownException.getMessage());
  }

  @Test
  void should_Throw_InvalidDataException_When_ScannedInjector_InjectionMethod_Is_Protected() {
    // given
    Class<?> rootClass = InjectorWithProtectedInjecton.class;

    // when
    InvalidDataException thrownException =
        assertThrows(InvalidDataException.class, () -> new KybContainer(rootClass));

    // then
    assertEquals(
        "An injection method cannot be protected: class org.kybinfrastructure.ioc.test_classes.injector_with_protected_injecton.InjectorWithProtectedInjecton.someService",
        thrownException.getMessage());
  }

  @Test
  void should_Throw_InvalidDataException_When_ScannedInjector_InjectionMethod_Is_Static() {
    // given
    Class<?> rootClass = InjectorWithStaticInjecton.class;

    // when
    InvalidDataException thrownException =
        assertThrows(InvalidDataException.class, () -> new KybContainer(rootClass));

    // then
    assertEquals(
        "An injection method cannot be static: class org.kybinfrastructure.ioc.test_classes.injector_with_static_injecton.InjectorWithStaticInjecton.someService",
        thrownException.getMessage());
  }

  @Test
  void should_Throw_InvalidDataException_When_ScannedInjector_InjectionMethod_ReturnType_Is_Primitive() {
    // given
    Class<?> rootClass = InjectorWithPrimitiveReturnInjecton.class;

    // when
    InvalidDataException thrownException =
        assertThrows(InvalidDataException.class, () -> new KybContainer(rootClass));

    // then
    assertEquals(
        "An injection method cannot have primitive return type: class org.kybinfrastructure.ioc.test_classes.injector_with_primititive_return_injecton.InjectorWithPrimitiveReturnInjecton.someService",
        thrownException.getMessage());
  }

  @Test
  void should_Throw_InvalidDataException_When_ScannedInjector_InjectionMethod_Has_Primitive_Param() {
    // given
    Class<?> rootClass = InjectorWithPrimitiveParamInjecton.class;

    // when
    InvalidDataException thrownException =
        assertThrows(InvalidDataException.class, () -> new KybContainer(rootClass));

    // then
    assertEquals(
        "An injection method cannot have primitive parameters types: class org.kybinfrastructure.ioc.test_classes.injector_with_primititive_param_injecton.InjectorWithPrimitiveParamInjecton.someService",
        thrownException.getMessage());
  }

  @Test
  void builder_Should_Return_BuilderInstance_Of_KybContainer() {
    // given
    Class<?> rootClass = SomeService.class;

    // when
    var actualResult = KybContainer.builder(rootClass);

    // then
    assertNotNull(actualResult);
    assertNotNull(actualResult.build());
  }

  @Test
  void builder_Should_Throw_IllegalArgumentException_When_GivenRootClass_Is_Null() {
    // given
    Class<?> rootClass = null;

    // when
    IllegalArgumentException thrownException =
        assertThrows(IllegalArgumentException.class, () -> KybContainer.builder(rootClass));

    // then
    assertEquals("rootClass cannot be null!", thrownException.getMessage());
  }

  @Test
  void get_Should_Return_InitializedInstance_By_GivenClassInstance() {
    // given
    KybContainer container = KybContainer.builder(SomeService.class).build();

    // when
    var actualResult = container.get(SomeService.class);

    // then
    assertNotNull(actualResult);
  }

  @Test
  void get_Should_Return_OptionalEmpty_When_No_ManagedInstance_Found_By_GivenClassInstance() {
    // given
    KybContainer container = KybContainer.builder(SomeService.class).build();

    // when
    var actualResult = container.get(KybContainerTest.class);

    // then
    assertTrue(actualResult.isEmpty());
  }

  @Test
  void getManagedClasses_ShouldReturn_All_KybContainer_Managed_Instances() {
    // given
    KybContainer container = KybContainer.builder(SomeService.class).build();

    // when
    var actualResult = container.getManagedClasses();

    // then
    assertEquals(Set.of(AnotherServiceImpl.class, SomeServiceImpl.class), actualResult);
  }

}
