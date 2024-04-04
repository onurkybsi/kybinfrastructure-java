package org.kybinfrastructure.task_manager;

import java.sql.Statement;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import lombok.extern.slf4j.Slf4j;

@Slf4j
final class Tmm1__create_task_table extends BaseJavaMigration { // NOSONAR

  @Override
  protected void init() {
    extractVersionAndDescription(getClass().getSimpleName(), "Tmm", false);
  }

  @Override
  public void migrate(Context context) throws Exception {
    try (Statement s = context.getConnection().createStatement()) {
      s.executeUpdate("""
          CREATE TABLE IF NOT EXISTS task_manager.task (
            id SERIAL PRIMARY KEY,
            name VARCHAR(50) NOT NULL,
            handler VARCHAR NOT NULL,
            status VARCHAR(50) NOT NULL,
            state JSONB,
            try_count SMALLINT NOT NULL,
            max_try_count SMALLINT NOT NULL,
            last_execution_date TIMESTAMP,
            creation_date TIMESTAMP NOT NULL
          );
          """);
    } catch (Exception e) {
      log.error("TaskManager migration {} unsuccessful: {}", getClass().getSimpleName(), e);
      throw e;
    }
  }

}
