package com.agrifake;

import com.agrifake.config.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableConfigurationProperties(MailProperties.class)
@SpringBootApplication
public class AgrifakeBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(AgrifakeBackendApplication.class, args);
  }

}