package org.kybinfrastructure.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;
import org.kybinfrastructure.exception.InvalidDataException;

final class InjectorValidator {

  static void assertInjectorClassValid(Class<?> injectorClass) {
    if (injectorClass.isInterface()) {
      throw new InvalidDataException(
          "An interface cannot be an injector: " + injectorClass.getName());
    }
    if (Modifier.isAbstract(injectorClass.getModifiers())) {
      throw new InvalidDataException(
          "An abstract class cannot be an injector: " + injectorClass.getName());
    }
    if (injectorClass.isMemberClass()) {
      throw new InvalidDataException(
          "A member class cannot be an injector: " + injectorClass.getName());
    }
    if (injectorClass.isLocalClass()) {
      throw new InvalidDataException(
          "A local class cannot be an injector: " + injectorClass.getName());
    }

    Constructor<?>[] ctors = injectorClass.getDeclaredConstructors();
    if (ctors.length != 1) {
      throw new InvalidDataException(
          "An injector class can only have default constructor: " + injectorClass.getName());
    }
    if (ctors[0].getParameterCount() != 0) {
      throw new InvalidDataException(
          "An injector class can only have default constructor: " + injectorClass.getName());
    }
  }

  static void assertInjectionMethodValid(Method injectionMethod) {
    if (Modifier.isPrivate(injectionMethod.getModifiers())) {
      throw new InvalidDataException("An injection method cannot be private: %s.%s",
          injectionMethod.getDeclaringClass(), injectionMethod.getName());
    }
    if (Modifier.isProtected(injectionMethod.getModifiers())) {
      throw new InvalidDataException("An injection method cannot be protected: %s.%s",
          injectionMethod.getDeclaringClass(), injectionMethod.getName());
    }
    if (Modifier.isStatic(injectionMethod.getModifiers())) {
      throw new InvalidDataException("An injection method cannot be static: %s.%s",
          injectionMethod.getDeclaringClass(), injectionMethod.getName());
    }
    if (injectionMethod.getReturnType().isPrimitive()) {
      throw new InvalidDataException("An injection method cannot have primitive return type: %s.%s",
          injectionMethod.getDeclaringClass(), injectionMethod.getName());
    }
    if (Stream.of(injectionMethod.getParameters()).anyMatch(p -> p.getType().isPrimitive())) {
      throw new InvalidDataException(
          "An injection method cannot have primitive parameters types: %s.%s",
          injectionMethod.getDeclaringClass(), injectionMethod.getName());
    }
  }

}
