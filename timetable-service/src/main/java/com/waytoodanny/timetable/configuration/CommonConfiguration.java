package com.waytoodanny.timetable.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.waytoodanny.UniversityProperties;
import com.waytoodanny.timetable.event.EventPublisher;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

  @Bean
  public ObjectMapper applicationJsonMapper() {
    var builder = new Jackson2ObjectMapperBuilder();
    builder.serializerByType(LocalTime.class, new JsonSerializer<LocalTime>() {
      @Override
      public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
      }
    });
    return builder.build();
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
