package org.kybinfrastructure.ioc;

import java.lang.reflect.Constructor;

class ManagedClass {

	private final Class<?> clazz;
	private final Constructor<?> ctr;

	private Class<?>[] ctrParams;

	ManagedClass(Class<?> classToManage, Constructor<?> constructor) {
		this.clazz = classToManage;
		this.ctr = constructor;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Constructor<?> getCtr() {
		return ctr;
	}

	public Class<?>[] getCtrParams() {
		return ctrParams;
	}

	public void setCtrParams(Class<?>[] ctrParams) {
		this.ctrParams = ctrParams;
	}

}
