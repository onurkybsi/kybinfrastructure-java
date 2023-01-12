package org.kybinfrastructure.ioc;

import org.kybinfrastructure.exceptions.KybInfrastructureException;
import org.kybinfrastructure.utils.validation.Assertions;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * <a href="https://en.wikipedia.org/wiki/Inversion_of_control">IoC container</a> solution of
 * <i>KybInfrastructure</i>
 */
public final class KybContainer {

	// TODO: I don't think that we need a thread-safe array ?
	private static Set<Class<?>> CLASSES = new HashSet<>();

	private KybContainer(Class<?> rootClass) {
		try {
			loadClasses(rootClass);
		} catch (ClassNotFoundException e) {
			throw new KybInfrastructureException("Loading is not successful", e);
		}
	}

	/**
	 * 
	 * @param rootClass
	 * @return
	 */
	public static KybContainer build(Class<?> rootClass) {
		Assertions.notNull(rootClass, "rootClass cannot be null!");
		return new KybContainer(rootClass);
	}

	private void loadClasses(Class<?> rootClass) throws ClassNotFoundException {
		String rootClassFilePath = locateRootClassFilePath(rootClass);
		File rootDirectory = new File(rootClassFilePath);
		File[] subFilesAndDirectoriesOfRoot = rootDirectory.listFiles();
		for (int i = 0; i < subFilesAndDirectoriesOfRoot.length; i++) {
			loadClassName(subFilesAndDirectoriesOfRoot[i], new StringBuilder());
		}
	}

	private static String locateRootClassFilePath(Class<?> rootClass) {
		return URLDecoder.decode(
				rootClass.getProtectionDomain().getCodeSource().getLocation().getFile(),
				StandardCharsets.UTF_8);
	}

	private static void loadClassName(File innerFile, StringBuilder buildPackageName)
			throws ClassNotFoundException {
		if (innerFile.isDirectory()) {
			buildPackageName.append(innerFile.getName());
			buildPackageName.append(".");

			File[] innerFilesOfInternalFile = innerFile.listFiles();
			for (int i = 0; i < innerFilesOfInternalFile.length; i++) {
				loadClassName(innerFilesOfInternalFile[i], buildPackageName);
			}
		} else {
			if (!innerFile.getName().endsWith(".class")) {
				return;
			}

			String classNameToLoad = String.format("%s%s", buildPackageName.toString(),
					innerFile.getName().substring(0, innerFile.getName().length() - 6 /* .class */));
			CLASSES.add(
					Class.forName(classNameToLoad, true, Thread.currentThread().getContextClassLoader()));
		}
	}

	public Set<Class<?>> getLoadedClasses() {
		return CLASSES;
	}
}
