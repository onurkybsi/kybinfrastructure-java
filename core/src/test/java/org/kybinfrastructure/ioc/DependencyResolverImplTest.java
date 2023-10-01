package org.kybinfrastructure.ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.kybinfrastructure.exception.InvalidDataException;
import org.kybinfrastructure.ioc.test_classes.TestClassA;
import org.kybinfrastructure.ioc.test_classes.TestClassB;
import org.kybinfrastructure.ioc.test_classes.TestClassC;
import org.kybinfrastructure.ioc.test_classes.TestClassD;
import org.kybinfrastructure.ioc.test_classes.TestClassE;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import example.service.SomeService;
import example.service.sub.AnotherService;

@SuppressWarnings({"java:S5778"})
class DependencyResolverImplTest {

    @Test
    void resolve_Extracts_ManagedClasses() {
        // given
        Set<Class<?>> classesToManage =
                KybContainer.builder(SomeService.class).build().getManagedClasses();

        // when
        Set<ManagedClass> actualResult = new DependencyResolverImpl().resolve(classesToManage);

        // then
        assertEquals(2, actualResult.size());
        assertTrue(actualResult.stream()
                .anyMatch(mc -> mc.getClazz().getName().equals("example.service.SomeServiceImpl")
                        && mc.getCtr().getParameterTypes().length == 1
                        && mc.getCtr().getParameterTypes()[0].equals(AnotherService.class)));
        assertTrue(actualResult.stream().anyMatch(
                mc -> mc.getClazz().getName().equals("example.service.sub.AnotherServiceImpl")
                        && mc.getCtr().getParameterTypes().length == 0));
    }

    @Test
    void resolve_Throws_InvalidDataException_When_OneOf_GivenClasses_Has_More_Than_One_Constructors() {
        // given
        Set<Class<?>> classesToManage = new HashSet<>(List.of(TestClassB.class));

        // when
        InvalidDataException thrownException = assertThrows(InvalidDataException.class,
                () -> new DependencyResolverImpl().resolve(classesToManage));

        // then
        assertEquals("Managed classes should have 'only' 1 constructor(%s has 2)"
                .formatted(TestClassB.class.getName()), thrownException.getMessage());
    }

    @Test
    void resolve_Throws_InvalidDataException_When_OneOf_GivenClasses_Has_NonManagedClass_As_Ctr_Param() {
        // given
        Set<Class<?>> classesToManage = new HashSet<>(List.of(TestClassC.class));

        // when
        InvalidDataException thrownException = assertThrows(InvalidDataException.class,
                () -> new DependencyResolverImpl().resolve(classesToManage));

        // then
        assertEquals(
                "Managed class has a nonmanaged class as a constructor parameter: %s has %s"
                        .formatted(TestClassC.class.getName(), TestClassA.class.getName()),
                thrownException.getMessage());
    }

    @Test
    void resolve_Throws_InvalidDataException_When_TwoOf_GivenClasses_Depend_EachOther() {
        // given
        Set<Class<?>> classesToManage = new HashSet<>(List.of(TestClassD.class, TestClassE.class));

        // when
        InvalidDataException thrownException = assertThrows(InvalidDataException.class,
                () -> new DependencyResolverImpl().resolve(classesToManage));

        // then
        assertEquals(
                "Interdependency has been detected between %s & %s"
                        .formatted(TestClassE.class.getName(), TestClassD.class.getName()),
                thrownException.getMessage());
    }

}
