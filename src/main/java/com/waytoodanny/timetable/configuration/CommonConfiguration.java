package com.waytoodanny.timetable.configuration;

import com.waytoodanny.UniversityProperties;
import com.waytoodanny.timetable.genetic.event.EventPublisher;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

  @Component
  @ConfigurationPropertiesBinding
  private static class LocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(String source) {
      return LocalTime.parse(source, DateTimeFormatter.ofPattern("HH:mm"));
    }
  }
}
