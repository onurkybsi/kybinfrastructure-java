package org.kybinfrastructure.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Annotation which specifies that the class is loaded by {@link KybContainer}.
 * </p>
 * 
 * @author Onur Kayabasi (onurbpm@outlook.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Impl {
}
