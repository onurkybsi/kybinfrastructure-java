package org.kybinfrastructure.task_manager;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.kybinfrastructure.task_manager.TaskManagerConfiguration.TaskManagerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskManager {

  private static final ScheduledExecutorService MAIN = Executors.newSingleThreadScheduledExecutor();

  private final PlatformTransactionManager transactionManager;
  private final ApplicationContext ctx;

  @PersistenceContext
  private EntityManager entityManager;

  TaskManager(PlatformTransactionManager transactionManager, ApplicationContext ctx,
      TaskManagerProperties properties) {
    this.transactionManager = transactionManager;
    this.ctx = ctx;

    MAIN.scheduleAtFixedRate(this::executeNext, 0, 2 /* TODO: Make this configurable! */,
        TimeUnit.SECONDS);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void create(String name, Class<? extends Consumer<Object>> handler, Object state,
      int maxTryCount) {
    assertCreationParametersValid(name, handler, maxTryCount);

    TaskEntity taskToCreate = TaskEntity.builder().name(name).handler(handler.getName())
        .status(TaskStatus.CREATED.toString()).state(state).tryCount(0).maxTryCount(maxTryCount)
        .creationDate(OffsetDateTime.now()).build();
    entityManager.persist(taskToCreate);
  }

  void executeNext() {
    var transactionTemplate = new TransactionTemplate(transactionManager,
        new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
    transactionTemplate.executeWithoutResult(s -> {
      try {
        var q = entityManager.createNativeQuery("""
            SELECT * FROM task_manager.task
            WHERE status = 'CREATED'
            LIMIT 1
            FOR UPDATE SKIP LOCKED;
              """);
        TaskEntity e = (TaskEntity) q.getResultList().get(0); // TODO: objectMapper.convertValue is
                                                              // needed
        var handler = (Consumer<Object>) ctx.getBean(Class.forName(e.getHandler()));
        handler.accept(e.getState());
      } catch (Exception e) {
        log.error("Execute next unsuccessful!", e);
      }
    });
  }

  private static void assertCreationParametersValid(String name,
      Class<? extends Consumer<Object>> handler, int maxTryCount) {
    Objects.requireNonNull(name, "name cannot be null!");
    if (name.length() == 0 || name.length() > 50) {
      throw new IllegalArgumentException("name length must be between 0 and 50!");
    }

    Objects.requireNonNull(handler, "handler cannot be null!");

    if (maxTryCount <= 0) {
      throw new IllegalArgumentException("maxTryCount must be greater than 0!");
    }
  }

}
