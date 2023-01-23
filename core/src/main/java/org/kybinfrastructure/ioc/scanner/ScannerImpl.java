package org.kybinfrastructure.ioc.scanner;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import org.kybinfrastructure.ioc.Scanner;
import org.kybinfrastructure.utils.validation.Assertions;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation for {@link Scanner} interface
 */
public class ScannerImpl implements Scanner {

	@Override
	public Set<Class<?>> scan(Class<?> rootClass) {
		Assertions.notNull(rootClass, "rootClass cannot be null!");

		try {
			String rootDirectoryPathToScan = extractRootDirectoryPath(rootClass);
			File rootDirectoryToScan = new File(rootDirectoryPathToScan);

			Set<Class<?>> foundClasses = new HashSet<>();
			File[] subFilesAndDirectoriesOfRoot = rootDirectoryToScan.listFiles();
			for (int i = 0; i < subFilesAndDirectoriesOfRoot.length; i++) {
				addClassByBuildingFullyQualifiedName(foundClasses, subFilesAndDirectoriesOfRoot[i],
						new StringBuilder(rootClass.getPackageName() + "."));
			}

			return foundClasses;
		} catch (Exception e) {
			throw new KybInfrastructureException("" /* TODO: Use a common way! */);
		}
	}

	private static String extractRootDirectoryPath(Class<?> rootClass) {
		String rootClassFilePath =
				rootClass.getResource(rootClass.getSimpleName() + ".class").getPath();
		return rootClassFilePath.substring(0,
				rootClassFilePath.length() - (rootClass.getSimpleName().length() + 7));
	}

	private static void addClassByBuildingFullyQualifiedName(Set<Class<?>> setToAdd, File file,
			StringBuilder builtPackageName) throws ClassNotFoundException {
		if (file.isDirectory()) {
			builtPackageName.append(file.getName());
			builtPackageName.append(".");

			File[] innerFiles = file.listFiles();
			for (int i = 0; i < innerFiles.length; i++) {
				addClassByBuildingFullyQualifiedName(setToAdd, innerFiles[i],
						new StringBuilder(builtPackageName));
			}
		} else {
			if (!file.getName().endsWith(".class")) {
				return;
			}

			String classNameToLoad = String.format("%s%s", builtPackageName.toString(),
					file.getName().substring(0, file.getName().length() - 6 /* .class */));
			setToAdd.add(
					Class.forName(classNameToLoad, true, Thread.currentThread().getContextClassLoader()));
		}
	}

}
