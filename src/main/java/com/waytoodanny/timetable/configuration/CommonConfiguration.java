package com.waytoodanny.timetable.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class CommonConfiguration {

  @Bean
  public Random random() {
    return new Random();
  }
}
