package org.kybinfrastructure.ioc;

import java.util.Set;

interface Scanner {

	Set<Class<? extends Injector>> scan(Class<?> rootClass);

}
