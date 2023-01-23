package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import org.kybinfrastructure.utils.validation.Assertions;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * <a href="https://en.wikipedia.org/wiki/Inversion_of_control">IoC container</a> solution of
 * <i>KybInfrastructure</i>
 */
public final class KybContainer {

	// TODO: I don't think that we need a thread-safe array ?
	private static Set<Class<?>> CLASSES = new HashSet<>();

	private static HashMap<Class<?>, Object> INSTANCES = new HashMap<>();

	private KybContainer(Class<?> rootClass) {
		try {
			loadAllClasses(rootClass);
			filterClassesToResolve();
			initInstances();
		} catch (ClassNotFoundException e) {
			throw new KybInfrastructureException("Loading is not successful", e);
		}
	}

	// #region builders
	/**
	 * 
	 * @param rootClass
	 * @return
	 */
	public static KybContainer build(Class<?> rootClass) {
		Assertions.notNull(rootClass, "rootClass cannot be null!");
		return new KybContainer(rootClass);
	}
	// #endregion

	// #region loading
	private void loadAllClasses(Class<?> rootClass) throws ClassNotFoundException {
		String rootDirectoryPathToScan = extractRootDirectoryPath(rootClass);
		File rootDirectoryToScan = new File(rootDirectoryPathToScan);

		File[] subFilesAndDirectoriesOfRoot = rootDirectoryToScan.listFiles();
		for (int i = 0; i < subFilesAndDirectoriesOfRoot.length; i++) {
			loadClassByBuildingFullyQualifiedName(subFilesAndDirectoriesOfRoot[i],
					new StringBuilder(rootClass.getPackageName() + "."));
		}
	}

	private static String extractRootDirectoryPath(Class<?> rootClass) {
		String rootClassFilePath =
				rootClass.getResource(rootClass.getSimpleName() + ".class").getPath();
		return rootClassFilePath.substring(0,
				rootClassFilePath.length() - (rootClass.getSimpleName().length() + 7));
	}

	private static void loadClassByBuildingFullyQualifiedName(File file,
			StringBuilder builtPackageName) throws ClassNotFoundException {
		if (file.isDirectory()) {
			builtPackageName.append(file.getName());
			builtPackageName.append(".");

			File[] innerFiles = file.listFiles();
			for (int i = 0; i < innerFiles.length; i++) {
				loadClassByBuildingFullyQualifiedName(innerFiles[i], new StringBuilder(builtPackageName));
			}
		} else {
			if (!file.getName().endsWith(".class")) {
				return;
			}

			String classNameToLoad = String.format("%s%s", builtPackageName.toString(),
					file.getName().substring(0, file.getName().length() - 6 /* .class */));
			CLASSES.add(
					Class.forName(classNameToLoad, true, Thread.currentThread().getContextClassLoader()));
		}
	}
	// #endregion

	// #region filtering
	private static void filterClassesToResolve() {
		Set<Class<?>> filtered = new HashSet<>();
		for (Class<?> loadedClass : CLASSES) {
			if (checkWhetherHasImplAnnotation(loadedClass)) {
				filtered.add(loadedClass);
			}
		}
		CLASSES = filtered;
	}

	private static boolean checkWhetherHasImplAnnotation(Class<?> loadedClass) {
		Annotation[] annotations = loadedClass.getAnnotations();
		for (int i = 0; i < annotations.length; i++) {
			if (Impl.class.equals(annotations[i].annotationType())) {
				return true;
			}
		}
		return false;
	}
	// #endregion

	// #region init
	private static void initInstances() {
		for (Class<?> classToInit : CLASSES) {
			try {
				Constructor<?> ctor = classToInit.getConstructors()[0];
				INSTANCES.put(classToInit, ctor.newInstance());
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new KybInfrastructureException("Init is not successful", e);
			}
		}
	}
	// #endregion

	public <T> T getImpl(Class<T> classInstance) {
		Object instance = INSTANCES.get(classInstance);
		if (instance == null) {
			throw new KybInfrastructureException("No implementation found by given class instance!");
		}
		if (classInstance.isInstance(instance)) {
			return (T) instance;
		}
		throw new KybInfrastructureException("No implementation found by given class instance!");
	}

}
