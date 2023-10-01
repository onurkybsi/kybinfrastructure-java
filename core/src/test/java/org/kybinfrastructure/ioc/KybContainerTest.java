package org.kybinfrastructure.ioc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import example.service.SomeService;
import example.service.sub.AnotherService;

class KybContainerTest {

  @BeforeEach
  void refresh() {
    Configurator.setAllLevels("", Level.ALL);
  }

  @Test
  void build() {
    KybContainer container = KybContainer.builder(SomeService.class).build();
    assertNotNull(container.get(SomeService.class));
    assertNotNull(container.get(AnotherService.class));
  }

}
