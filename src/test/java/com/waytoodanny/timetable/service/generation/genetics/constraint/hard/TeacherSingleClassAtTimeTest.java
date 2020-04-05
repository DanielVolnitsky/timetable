package com.waytoodanny.timetable.service.generation.genetics.constraint.hard;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.Teacher;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.Gene;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TeacherSingleClassAtTimeTest {

  Room room1 = new Room("1", 10);
  Room room2 = new Room("2", 10);
  Room room3 = new Room("3", 10);

  Teacher teacher1 = Teacher.builder().name("t1").build();
  Teacher teacher2 = Teacher.builder().name("t2").build();
  Teacher teacher3 = Teacher.builder().name("t3").build();

  TeachingClass teachingClass1 = TeachingClass.builder().teacher(teacher1).build();
  TeachingClass teachingClass2 = TeachingClass.builder().teacher(teacher2).build();
  TeachingClass teachingClass3 = TeachingClass.builder().teacher(teacher3).build();

  TeacherSingleClassAtTime sut = new TeacherSingleClassAtTime();

  @Test
  void fitnessWhenEachTeacherOccursOncePerTimeSlot() {
    var chromosome = new Chromosome(
        List.of(
            Gene.builder()
                .timeSlot(1)
                .teachingTuple(new Gene.Tuple(teachingClass1, room1)).build(),
            Gene.builder()
                .timeSlot(2)
                .teachingTuple(new Gene.Tuple(teachingClass1, room1))
                .teachingTuple(new Gene.Tuple(teachingClass2, room2)).build(),
            Gene.builder()
                .timeSlot(3)
                .teachingTuple(new Gene.Tuple(teachingClass3, room1))
                .teachingTuple(new Gene.Tuple(teachingClass2, room2))
                .teachingTuple(new Gene.Tuple(teachingClass1, room3)).build())
    );

    FitnessFunction result = sut.fitness(chromosome, FitnessFunction.INITIAL);
    assertThat(result).isEqualTo(new FitnessFunction(100));
  }

  @Test
  void fitnessWhenSomeTeachersOccurMoreThanOncePerTimeSlot() {
    var chromosome = new Chromosome(
        List.of(
            Gene.builder()
                .timeSlot(1)
                .teachingTuple(new Gene.Tuple(teachingClass1, room1)).build(),
            Gene.builder()
                .timeSlot(2)
                .teachingTuple(new Gene.Tuple(teachingClass2, room1))
                .teachingTuple(new Gene.Tuple(teachingClass2, room2)).build(),
            Gene.builder()
                .timeSlot(3)
                .teachingTuple(new Gene.Tuple(teachingClass3, room1))
                .teachingTuple(new Gene.Tuple(teachingClass2, room2))
                .teachingTuple(new Gene.Tuple(teachingClass1, room3)).build())
    );

    FitnessFunction result = sut.fitness(chromosome, FitnessFunction.INITIAL);
    assertThat(result).isEqualTo(FitnessFunction.UNACCEPTABLE);
  }

  @Test
  void fitnessWhenEveryTeacherOccursMoreThanOncePerTimeSlot() {
    var chromosome = new Chromosome(
        List.of(
            Gene.builder()
                .timeSlot(1)
                .teachingTuple(new Gene.Tuple(teachingClass1, room1))
                .teachingTuple(new Gene.Tuple(teachingClass1, room1)).build(),
            Gene.builder()
                .timeSlot(2)
                .teachingTuple(new Gene.Tuple(teachingClass2, room2))
                .teachingTuple(new Gene.Tuple(teachingClass2, room2)).build(),
            Gene.builder()
                .timeSlot(3)
                .teachingTuple(new Gene.Tuple(teachingClass3, room3))
                .teachingTuple(new Gene.Tuple(teachingClass2, room3))
                .teachingTuple(new Gene.Tuple(teachingClass1, room3)).build())
    );

    FitnessFunction result = sut.fitness(chromosome, FitnessFunction.INITIAL);
    assertThat(result).isEqualTo(FitnessFunction.UNACCEPTABLE);
  }
}