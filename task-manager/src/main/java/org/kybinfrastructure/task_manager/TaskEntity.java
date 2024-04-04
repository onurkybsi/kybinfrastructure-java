package org.kybinfrastructure.task_manager;

import java.time.OffsetDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "task", schema = "task_manager")
class TaskEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(min = 0, max = 50)
  @Column(name = "name")
  private String name;

  @NotNull
  @Column(name = "handler")
  private String handler;

  @NotNull
  @Size(min = 0, max = 50)
  @Column(name = "status")
  private String status;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "state")
  private Object state;

  @Column(name = "try_count")
  private Integer tryCount;

  @Column(name = "max_try_count")
  private Integer maxTryCount;

  @Column(name = "last_execution_date")
  private OffsetDateTime lastExecutionDate;

  @NotNull
  @Column(name = "creation_date")
  private OffsetDateTime creationDate;

}
