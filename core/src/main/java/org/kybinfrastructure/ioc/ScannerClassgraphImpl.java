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
      ClassInfoList classInfoWithImplAnnotation =
          scanResult.getClassesWithAnnotation(Impl.class.getName());

      Set<Class<?>> scannedClasses = new HashSet<>();
      for (int i = 0; i < classInfoWithImplAnnotation.size(); i++) {
        scannedClasses.add(classInfoWithImplAnnotation.get(i).loadClass());
      }
      return scannedClasses;
    } catch (Exception e) {
      throw new KybInfrastructureException(
          "Class path scanning by the given root class(%s) is unsucessful!".formatted(rootClass),
          e);
    }
  }

}
