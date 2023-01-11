package org.kybinfrastructure;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class Main {
	private static List<Class<?>> locatedClasses = new ArrayList<>();

	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println("Hello World!");
		load(Main.class);
	}

	// 1. Request a root class from client
	private static void load(Class<Main> rootClass) {
		// 2. Find the directory of root class
		String rootClassDirectory =
				URLDecoder.decode(rootClass.getProtectionDomain().getCodeSource().getLocation().getFile(),
						StandardCharsets.UTF_8);

		// 3.
		File fileInstanceOfRootClassDirectory = new File(rootClassDirectory);
		for (var file : fileInstanceOfRootClassDirectory.listFiles()) {
			findClassToInject(file, new StringBuilder());
		}
	}

	private static void findClassToInject(File file, StringBuilder packageName) {
		if (file.isDirectory()) {
			packageName.append(file.getName());
			packageName.append(".");
			for (var innerFile : file.listFiles()) {
				findClassToInject(innerFile, packageName);
			}
		} else {
			if (!file.getName().endsWith(".class")) {
				return;
			}

			String className = String.format("%s%s", packageName.toString(),
					file.getName().substring(0, file.getName().length() - 6 /* .class */));
			System.out.println(className);
		}
	}
}
