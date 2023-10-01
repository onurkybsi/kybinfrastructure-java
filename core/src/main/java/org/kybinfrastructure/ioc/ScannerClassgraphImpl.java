package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashSet;
import java.util.Set;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

final class ScannerClassgraphImpl implements Scanner {

  private static final Logger LOGGER = LoggerFactory.getLogger(KybContainer.class);

  ScannerClassgraphImpl() {}

  @Override
  @SuppressWarnings({"unchecked"})
  public Set<Class<? extends Injector>> scan(Class<?> rootClass) {
    try (ScanResult scanResult =
        new ClassGraph().enableAllInfo().acceptPackages(rootClass.getPackageName()).scan()) {
      ClassInfoList classInfoListWithInjectorSuper =
          scanResult.getClassesImplementing(Injector.class);

      Set<Class<? extends Injector>> injectorClasses = new HashSet<>();
      for (int i = 0; i < classInfoListWithInjectorSuper.size(); i++) {
        Class<? extends Injector> injectorClass =
            (Class<? extends Injector>) classInfoListWithInjectorSuper.get(i).loadClass();

        LOGGER.debug("{} injector class loaded!", injectorClass);
        injectorClasses.add(injectorClass);
      }

      return injectorClasses;
    } catch (Exception e) {
      throw new UnexpectedException("Injectors couldn't be extracted!", e);
    }
  }

}
