package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.UnexpectedException;
import org.kybinfrastructure.utils.ClassUtils;
import org.kybinfrastructure.utils.validation.Assertions;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Default implementation for {@link Scanner}
 */
class ScannerImpl implements Scanner {

	@Override
	public Set<Class<?>> scan(Class<?> rootClass) {
		Assertions.notNull(rootClass, "rootClass cannot be null!");

		try {
			String rootDirectoryPathToScan = ClassUtils.resolveClassDirectoryPath(rootClass);
			File rootDirectoryToScan = new File(rootDirectoryPathToScan);

			Set<Class<?>> foundClasses = new HashSet<>();
			File[] subFilesAndDirectoriesOfRoot = rootDirectoryToScan.listFiles();
			for (int i = 0; i < subFilesAndDirectoriesOfRoot.length; i++) {
				loadClassByBuildingItsName(foundClasses, subFilesAndDirectoriesOfRoot[i],
						new StringBuilder(rootClass.getPackageName() + "."));
			}

			return foundClasses;
		} catch (Exception e) {
			throw new UnexpectedException(
					"Scanning is not successful for the root class: " + rootClass.getName(), e);
		}
	}

	// TODO: Not a good approach.
	// We should NOT even load if the type doesn't safisfy the filtering condition
	@Override
	public Set<Class<?>> scan(Class<?> rootClass, Predicate<Class<?>> filter) {
		Assertions.notNull(filter, "filter cannot be null!");

		Set<Class<?>> scannedClasses = scan(rootClass);

		Set<Class<?>> filteredScannedClasses = new HashSet<>();
		for (Class<?> scannedClass : scannedClasses) {
			if (filter.test(scannedClass)) {
				filteredScannedClasses.add(scannedClass);
			}
		}

		return filteredScannedClasses;
	}

	private static void loadClassByBuildingItsName(Set<Class<?>> setToAdd, File file,
			StringBuilder builtPackageName) throws ClassNotFoundException {
		if (file.isDirectory()) {
			builtPackageName.append(file.getName());
			builtPackageName.append(".");

			File[] innerFiles = file.listFiles();
			for (int i = 0; i < innerFiles.length; i++) {
				loadClassByBuildingItsName(setToAdd, innerFiles[i], new StringBuilder(builtPackageName));
			}
		} else {
			if (!file.getName().endsWith(ClassUtils.CLASS_FILE_EXTENSION)) {
				return;
			}

			String classNameToLoad = String.format("%s%s", builtPackageName.toString(), file.getName()
					.substring(0, file.getName().length() - ClassUtils.CLASS_FILE_EXTENSION.length()));
			setToAdd.add(Class.forName(classNameToLoad));
		}
	}

}
