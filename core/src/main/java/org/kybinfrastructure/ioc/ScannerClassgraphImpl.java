package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.KybInfrastructureException;
import java.util.HashSet;
import java.util.Set;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

final class ScannerClassgraphImpl implements Scanner {

  ScannerClassgraphImpl() {}

  @Override
  public Set<Class<?>> scan(Class<?> rootClass) {
    try (ScanResult scanResult =
        new ClassGraph().enableAllInfo().acceptPackages(rootClass.getPackageName()).scan()) {
      ClassInfoList classInfoWithInjectorSuper =
          scanResult.getClassesWithAnnotation(Impl.class.getName());

      Set<Class<?>> injectorClasses = new HashSet<>();
      for (int i = 0; i < classInfoWithInjectorSuper.size(); i++) {
        injectorClasses.add(classInfoWithInjectorSuper.get(i).loadClass());
      }
      return injectorClasses;
    } catch (Exception e) {
      throw new KybInfrastructureException("Injector couldn't be extracted!", e);
    }
  }

}
