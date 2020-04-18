package com.waytoodanny.timetable.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@EnableConfigurationProperties(GeneticsProperties.class)
@Slf4j
public class GeneticsConfiguration {

  @Bean
  public Random random() {
    return new Random();
  }
}
