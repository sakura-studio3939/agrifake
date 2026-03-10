package com.agrifake;

import com.agrifake.config.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableConfigurationProperties(MailProperties.class)
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.agrifake.repository") // 明示化
public class AgrifakeBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(AgrifakeBackendApplication.class, args);
  }

}