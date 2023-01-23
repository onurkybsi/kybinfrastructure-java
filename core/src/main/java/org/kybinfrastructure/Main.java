package org.kybinfrastructure;

import org.kybinfrastructure.ioc.KybContainerBuilder;
import org.kybinfrastructure.ioc.test_classes.SomeImpl;
import java.util.ArrayList;
import java.util.List;

public final class Main {
	private static List<Class<?>> locatedClasses = new ArrayList<>();

	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println("Hello World!");
		var container = KybContainerBuilder.build(Main.class);
		SomeImpl initialized = container.getImpl(SomeImpl.class);
		initialized.sayHi("Onur");
	}

}
