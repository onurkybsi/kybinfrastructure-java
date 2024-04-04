package org.kybinfrastructure.task_manager;

import org.flywaydb.core.Flyway;
import org.kybinfrastructure.task_manager.TaskManagerConfiguration.TaskManagerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableConfigurationProperties(TaskManagerProperties.class)
class TaskManagerConfiguration {

  @Bean
  TaskManager taskManager(@Value("${spring.datasource.url}") String url,
      @Value("${spring.datasource.username}") String username,
      @Value("${spring.datasource.password}") String password,
      PlatformTransactionManager transactionManager, ApplicationContext ctx,
      TaskManagerProperties properties) {
    migrate(url, username, password);
    return new TaskManager(transactionManager, ctx, properties);
  }

  private static void migrate(String url, String username, String password) {
    Flyway flyway =
        Flyway.configure().dataSource(url, username, password).defaultSchema("task_manager")
            .javaMigrationClassProvider(null).javaMigrations(new Tmm1__create_task_table()).load();
    flyway.migrate();
  }

  @ConfigurationProperties("task-manager")
  static record TaskManagerProperties(Integer threadPoolSize, Integer delayInFailure) {
  }

}
