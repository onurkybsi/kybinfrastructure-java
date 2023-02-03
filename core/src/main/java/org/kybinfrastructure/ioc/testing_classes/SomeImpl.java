package org.kybinfrastructure.ioc.testing_classes;

import org.kybinfrastructure.ioc.Impl;

@Impl
public class SomeImpl {

	private final SomeImplX someImplX;
	private final SomeImplXX someImplXX;

	public SomeImpl(SomeImplX someImplX, SomeImplXX someImplXX) {
		this.someImplX = someImplX;
		this.someImplXX = someImplXX;
	}

}
