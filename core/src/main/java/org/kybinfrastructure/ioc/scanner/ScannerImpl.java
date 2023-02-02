package org.kybinfrastructure.ioc.scanner;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import org.kybinfrastructure.utils.validation.Assertions;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Default implementation for {@link Scanner} interface
 */
public class ScannerImpl implements Scanner {

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
			throw new KybInfrastructureException(
					"Scanning is not successful & rootClass: " + rootClass.getName(), e);
		}
	}

	@Override
	public Set<Class<?>> scan(Class<?> rootClass, Predicate<Class<?>> filter) {
		Assertions.notNull(rootClass, "rootClass cannot be null!");
		Assertions.notNull(filter, "filter cannot be null!");

		Set<Class<?>> scannedClasses = new HashSet<>();
		for (Class<?> scannedClass : scan(rootClass)) {
			if (filter.test(scannedClass)) {
				scannedClasses.add(scannedClass);
			}
		}

		return scannedClasses;
	}

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
