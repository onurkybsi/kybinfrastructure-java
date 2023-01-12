package org.kybinfrastructure;

import org.kybinfrastructure.ioc.KybContainer;
import java.util.ArrayList;
import java.util.List;

public final class Main {
	private static List<Class<?>> locatedClasses = new ArrayList<>();

	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println("Hello World!");
		var container = KybContainer.build(Main.class);
		for (Class<?> loadedClass : container.getLoadedClasses()) {
			System.out.println(loadedClass.getName());
		}
	}

}
