package com.waytoodanny.timetable;

import com.waytoodanny.timetable.configuration.UniversityProperties;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.timetable.Timetable;
import com.waytoodanny.timetable.domain.university.*;
import com.waytoodanny.timetable.service.generation.TimetableGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties({
    UniversityProperties.class
})
public class Application implements CommandLineRunner {

  @Autowired
  private TimetableGenerator timetableGenerator;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) {
    var rooms = Rooms.of(
        new Room("1", 10),
        new Room("2", 15),
        new Room("3", 20));

    var subject1 = new Subject("S1");
    var subject2 = new Subject("S2");
    var teacher1 = Teacher.builder().name("T1").build();
    var teacher2 = Teacher.builder().name("T2").build();
    var group1 = new StudentGroup("SG1", 11);
    var group2 = new StudentGroup("SG2", 10);
    var group3 = new StudentGroup("SG2", 16);
    var class1 = TeachingClass.builder()
        .subject(subject1)
        .teacher(teacher1)
        .group(group1)
        .classesPerWeek(2)
        .build();
    var class2 = TeachingClass.builder()
        .subject(subject2)
        .teacher(teacher2)
        .group(group2)
        .classesPerWeek(2)
        .build();
    var class3 = TeachingClass.builder()
        .subject(subject1)
        .teacher(teacher2)
        .group(group3)
        .classesPerWeek(2)
        .build();
    var input = new InputData(Set.of(class1, class2, class3), rooms);

    Timetable timetable = timetableGenerator.timetable(input);
  }
}
