package org.kybinfrastructure.ioc.test_classes;

import org.kybinfrastructure.ioc.Impl;

@Impl
public class SomeImpl {

	public void sayHi(String name) {
		System.out.format("Hi %s !", name);
	}

}