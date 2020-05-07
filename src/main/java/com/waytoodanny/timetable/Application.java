package com.waytoodanny.timetable;

import com.waytoodanny.timetable.genetic.domain.university.AvailableRooms;
import com.waytoodanny.timetable.genetic.domain.university.ClassType;
import com.waytoodanny.timetable.genetic.domain.university.Room;
import com.waytoodanny.timetable.genetic.domain.university.StudentGroup;
import com.waytoodanny.timetable.genetic.domain.university.Subject;
import com.waytoodanny.timetable.genetic.domain.university.Teacher;
import com.waytoodanny.timetable.genetic.domain.university.TeachingClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class Application implements CommandLineRunner {

  @Autowired
  private TimetableSupplier timetableSupplier;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) {
    var rooms = AvailableRooms.of(
        new Room("1", 20),
        new Room("2", 20));

    var subject1 = new Subject("S1");
    var subject2 = new Subject("S2");
    var subject3 = new Subject("S3");

    var teacher1 = Teacher.builder().name("T1").build();
    var teacher2 = Teacher.builder().name("T2").build();

    var group1 = new StudentGroup("SG1", 11);
    var group2 = new StudentGroup("SG2", 10);

    var class1 = TeachingClass.builder()
        .teacher(teacher1).subject(subject1).group(group1).classesPerWeek(4)
        .type(ClassType.LECTURE)
        .build();
    var class2 = TeachingClass.builder()
        .teacher(teacher1).subject(subject1).group(group2).classesPerWeek(4)
        .type(ClassType.LECTURE)
        .build();
    var class3 = TeachingClass.builder()
        .teacher(teacher1).subject(subject3).group(group1).classesPerWeek(4)
        .type(ClassType.PRACTICE)
        .build();
    var class4 = TeachingClass.builder()
        .teacher(teacher2).subject(subject3).group(group2).classesPerWeek(4)
        .type(ClassType.LECTURE)
        .build();
    var class5 = TeachingClass.builder()
        .teacher(teacher2).subject(subject2).group(group1).classesPerWeek(4)
        .type(ClassType.PRACTICE)
        .build();
    var class6 = TeachingClass.builder()
        .teacher(teacher2).subject(subject2).group(group2).classesPerWeek(4)
        .type(ClassType.PRACTICE)
        .build();
    var compositeClass1 = TeachingClass.builder()
        .teacher(teacher2).teacher(teacher1)
        .subject(subject2)
        .group(group2)
        .type(ClassType.LECTURE)
        .classesPerWeek(1)
        .build();

    var input = new InputData(Set.of(
        class1,
        class2,
        class3,
        class4,
        class5,
        class6,
        compositeClass1), rooms);

    Timetable timetable = timetableSupplier.timetable(input);
  }
}
