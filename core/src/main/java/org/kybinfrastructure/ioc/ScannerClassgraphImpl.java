package org.kybinfrastructure.ioc;

import java.util.LinkedHashSet;
import java.util.Set;
import org.kybinfrastructure.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

final class ScannerClassgraphImpl implements Scanner {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScannerClassgraphImpl.class);

  @Override
  public Set<Class<?>> scanForInjectorClasses(Class<?> rootClass) {
    try (ScanResult scanResult =
        new ClassGraph().enableAllInfo().acceptPackages(rootClass.getPackageName()).scan()) {
      var classesWithInjectorAnnotation = scanResult.getClassesWithAnnotation(Injector.class);

      Set<Class<?>> injectorClasses = new LinkedHashSet<>(classesWithInjectorAnnotation.size());
      for (var classWithInjectorAnnotation : classesWithInjectorAnnotation) {
        injectorClasses.add(classWithInjectorAnnotation.loadClass());
        LOGGER.debug("{} injector class was loaded!", classWithInjectorAnnotation.getName());
      }
      return injectorClasses;
    } catch (Exception e) {
      throw new UnexpectedException("Injector classes couldn't be extracted!", e);
    }
  }

}
