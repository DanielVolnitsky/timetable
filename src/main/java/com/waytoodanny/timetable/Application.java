package com.waytoodanny.timetable;

import com.waytoodanny.timetable.configuration.GeneticsConfiguration;
import com.waytoodanny.timetable.configuration.UniversityConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    GeneticsConfiguration.class,
    UniversityConfiguration.class
})
public class Application implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) {

  }
}
