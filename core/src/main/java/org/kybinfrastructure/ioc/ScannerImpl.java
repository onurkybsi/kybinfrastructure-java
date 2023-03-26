package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exception.UnexpectedException;
import org.kybinfrastructure.utils.validation.Assertions;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Default implementation for {@link Scanner}
 */
class ScannerImpl implements Scanner {

	private static final String CLASS_FILE_EXTENSION = ".class";

	@Override
	public Set<Class<?>> scan(Class<?> rootClass) {
		Assertions.notNull(rootClass, "rootClass cannot be null!");

		try {
			String rootDirectoryPathToScan = extractRootDirectoryPath(rootClass);
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

	// TODO: This doesn't work for local classes!
	private static String extractRootDirectoryPath(Class<?> rootClass) {
		String rootClassFilePath =
				rootClass.getResource(rootClass.getSimpleName() + CLASS_FILE_EXTENSION).getPath();
		return rootClassFilePath.substring(0, rootClassFilePath.length()
				- (rootClass.getSimpleName().length() + CLASS_FILE_EXTENSION.length()));
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
			if (!file.getName().endsWith(CLASS_FILE_EXTENSION)) {
				return;
			}

			String classNameToLoad = String.format("%s%s", builtPackageName.toString(),
					file.getName().substring(0, file.getName().length() - CLASS_FILE_EXTENSION.length()));
			setToAdd.add(Class.forName(classNameToLoad));
		}
	}

}
