package org.kybinfrastructure.ioc;

import java.util.HashMap;
import java.util.Map;

public class InitializationConfig {

	private final boolean useMaintainingContainer;
	private final Map<Class<?>, Object> maintainingContainer;

	public InitializationConfig() {
		this.useMaintainingContainer = false;
		this.maintainingContainer = new HashMap<>();
	}


	public InitializationConfig(Map<Class<?>, Object> maintainingContainer,
			Boolean useMaintainingContainer) {
		this.maintainingContainer = maintainingContainer;
		this.useMaintainingContainer = useMaintainingContainer;
	}


	public boolean useMaintainingContainer() {
		return useMaintainingContainer;
	}


	public Map<Class<?>, Object> getMaintainingContainer() {
		return maintainingContainer;
	}

}
