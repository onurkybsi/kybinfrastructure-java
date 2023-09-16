package org.kybinfrastructure.ioc;

import java.util.Set;

interface DependencyResolver {

	Set<ManagedClass> resolve(Set<Class<?>> classesToManage);

}
