package com.waytoodanny.timetable.configuration;

import com.waytoodanny.UniversityProperties;
import com.waytoodanny.event.EventPublisher;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {

  @Bean
  @ConfigurationProperties("university")
  public UniversityProperties universityProperties() {
    return new UniversityProperties();
  }

  @Bean
  public EventPublisher eventPublisher(
      ApplicationEventPublisher springEventPublisher) {
    return springEventPublisher::publishEvent;
  }
}
