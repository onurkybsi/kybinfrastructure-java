package org.kybinfrastructure.utils;

import org.kybinfrastructure.exception.UnexpectedException;
import org.kybinfrastructure.utils.validation.Assertions;
import java.net.URL;

/**
 * Provides utilities for {@code Class<?>} instances.
 *
 * @author Onur Kayabasi
 */
public final class ClassUtils {

  private ClassUtils() {
    throw new UnexpectedException("This class is a utility class, cannot be initiated!");
  }

  public static final String CLASS_FILE_EXTENSION = ".class";

  /**
   * Returns the path of the directory which compiled class file of the given class is located.
   *
   * @param clazz class instance to be resolved
   * @return the path of the directory
   * @throws IllegalArgumentException if the given {@code clazz} value is null
   */
  public static String resolveClassDirectoryPath(Class<?> clazz) {
    Assertions.notNull(clazz, "clazz cannot be null!");
    URL targetResource = clazz.isMemberClass() || clazz.isLocalClass()
        ? clazz.getResource(clazz.getEnclosingClass().getSimpleName() + CLASS_FILE_EXTENSION)
        : clazz.getResource(clazz.getSimpleName() + CLASS_FILE_EXTENSION);
    String targetClassOrEnclosingClassPath = targetResource.getPath();
    return targetClassOrEnclosingClassPath.substring(0,
        targetClassOrEnclosingClassPath.lastIndexOf('/'));
  }

}
