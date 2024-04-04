package org.kybinfrastructure.task_manager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Annotation which needs to be used by the {@link TaskManager} clients to enable it.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(TaskManagerConfiguration.class)
public @interface EnableTaskManager {
}
