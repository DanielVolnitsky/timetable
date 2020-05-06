package com.waytoodanny;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@EnableConfigurationProperties(GeneticsProperties.class)
@ComponentScan
public class GeneticConfiguration {

  @Bean
  public Random random() {
    return new Random();
  }
}
