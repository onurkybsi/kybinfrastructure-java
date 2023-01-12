package org.kybinfrastructure.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interfaces which has this annotation can act as a proxy to the {@link KybContainer}. So the
 * dependency to {@link KybContainer} can be decreased.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KybContainerProxy {
}
