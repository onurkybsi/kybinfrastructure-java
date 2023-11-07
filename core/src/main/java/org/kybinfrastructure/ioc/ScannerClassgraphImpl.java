package org.kybinfrastructure.ioc;

import java.util.HashSet;
import java.util.Set;
import org.kybinfrastructure.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

final class ScannerClassgraphImpl implements Scanner {

  private static final Logger LOGGER = LoggerFactory.getLogger(KybContainer.class);

  @Override
  public Set<Class<?>> scan(Class<?> rootClass) {
    try (ScanResult scanResult =
        new ClassGraph().enableAllInfo().acceptPackages(rootClass.getPackageName()).scan()) {
      ClassInfoList classesWithAnnotation = scanResult.getClassesWithAnnotation(Injector.class);

      Set<Class<?>> injectorClasses = new HashSet<>();
      for (int i = 0; i < classesWithAnnotation.size(); i++) {
        Class<?> injectorClass = classesWithAnnotation.get(i).loadClass();

        LOGGER.debug("{} injector class was loaded!", injectorClass);
        injectorClasses.add(injectorClass);
      }

      return injectorClasses;
    } catch (Exception e) {
      throw new UnexpectedException("Injectors couldn't be extracted!", e);
    }
  }

}
