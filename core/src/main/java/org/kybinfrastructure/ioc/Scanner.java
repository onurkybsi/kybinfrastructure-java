package org.kybinfrastructure.ioc;

import java.util.Set;

interface Scanner {

	Set<Class<?>> scanForInjectorClasses(Class<?> rootClass);

}
