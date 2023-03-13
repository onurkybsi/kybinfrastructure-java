package org.kybinfrastructure;

import org.kybinfrastructure.ioc.KybContainerBuilder;
import org.kybinfrastructure.ioc.testing_classes.SomeImpl;
import org.kybinfrastructure.ioc.testing_classes.SomeImplX;
import org.kybinfrastructure.ioc.testing_classes.SomeImplXX;
import org.kybinfrastructure.ioc.testing_classes.SomeImplXXX;
import java.util.ArrayList;
import java.util.List;

public final class Main {
	private static List<Class<?>> locatedClasses = new ArrayList<>();

	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println("Hello World!");

		var container = KybContainerBuilder.build(Main.class);

		var someImpl = container.getImpl(SomeImpl.class);
		var someImplX = container.getImpl(SomeImplX.class);
		var someImplXX = container.getImpl(SomeImplXX.class);
		System.out.println(someImpl);
		System.out.println(someImplX);
		System.out.println(someImplXX);

		var someImpl_2 = container.getImpl(SomeImpl.class);
		var someImplX_2 = container.getImpl(SomeImplX.class);
		var someImplXX_2 = container.getImpl(SomeImplXX.class);
		System.out.println(someImpl_2);
		System.out.println(someImplX_2);
		System.out.println(someImplXX_2);

		var someImplXXX = container.getImpl(SomeImplXXX.class);
		System.out.println(someImplXXX);
	}

}
