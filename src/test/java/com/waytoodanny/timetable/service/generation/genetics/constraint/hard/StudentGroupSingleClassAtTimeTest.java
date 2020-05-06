//package com.waytoodanny.timetable.service.generation.genetics.constraint.hard;
//
//import domain.university.Room;
//import domain.university.StudentGroup;
//import domain.university.TeachingClass;
//import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
//import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
//import com.waytoodanny.timetable.service.generation.genetics.entity.Gene;
//import com.waytoodanny.timetable.service.generation.genetics.entity.SettledClass;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class StudentGroupSingleClassAtTimeTest {
//
//  Room room1 = new Room("1", 10);
//  Room room2 = new Room("2", 10);
//  Room room3 = new Room("3", 10);
//
//  StudentGroup sg1 = new StudentGroup("sg1", 10);
//  StudentGroup sg2 = new StudentGroup("sg2", 10);
//  StudentGroup sg3 = new StudentGroup("sg3", 10);
//
//  TeachingClass teachingClass1 = TeachingClass.builder().group(sg1).build();
//  TeachingClass teachingClass2 = TeachingClass.builder().group(sg2).build();
//  TeachingClass teachingClass3 = TeachingClass.builder().group(sg3).build();
//  TeachingClass teachingClass4 = TeachingClass.builder().group(sg1).build();
//
//  StudentGroupSingleClassAtTime sut = new StudentGroupSingleClassAtTime();
//
//  @Test
//  void fitnessWhenEachGroupOccursOncePerTimeSlot() {
//    var chromosome = new Chromosome(
//        List.of(
//            Gene.builder()
//                .timeSlot(1)
//                .settledClass(new SettledClass(teachingClass1, room1)).build(),
//            Gene.builder()
//                .timeSlot(2)
//                .settledClass(new SettledClass(teachingClass1, room1))
//                .settledClass(new SettledClass(teachingClass2, room2)).build(),
//            Gene.builder()
//                .timeSlot(3)
//                .settledClass(new SettledClass(teachingClass3, room1))
//                .settledClass(new SettledClass(teachingClass2, room2))
//                .settledClass(new SettledClass(teachingClass1, room3)).build())
//    );
//
//    FitnessFunction result = sut.fitness(chromosome, FitnessFunction.INITIAL);
//    assertThat(result).isEqualTo(FitnessFunction.INITIAL.plus(sut.weight()));
//  }
//
//  @Test
//  void fitnessWhenSomeTeachersOccurMoreThanOncePerTimeSlot() {
//    var chromosome = new Chromosome(
//        List.of(
//            Gene.builder()
//                .timeSlot(1)
//                .settledClass(new SettledClass(teachingClass2, room1)).build(),
//            Gene.builder()
//                .timeSlot(2)
//                .settledClass(new SettledClass(teachingClass1, room1))
//                .settledClass(new SettledClass(teachingClass4, room2)).build(),
//            Gene.builder()
//                .timeSlot(3)
//                .settledClass(new SettledClass(teachingClass3, room1))
//                .settledClass(new SettledClass(teachingClass2, room2))
//                .settledClass(new SettledClass(teachingClass1, room3)).build())
//    );
//
//    FitnessFunction result = sut.fitness(chromosome, FitnessFunction.INITIAL);
//    assertThat(result).isEqualTo(FitnessFunction.INITIAL);
//  }
//
//  @Test
//  void fitnessWhenEveryTeacherOccursMoreThanOncePerTimeSlot() {
//    var chromosome = new Chromosome(
//        List.of(
//            Gene.builder()
//                .timeSlot(1)
//                .settledClass(new SettledClass(teachingClass1, room1))
//                .settledClass(new SettledClass(teachingClass4, room1)).build(),
//            Gene.builder()
//                .timeSlot(2)
//                .settledClass(new SettledClass(teachingClass2, room2))
//                .settledClass(new SettledClass(teachingClass2, room2)).build(),
//            Gene.builder()
//                .timeSlot(3)
//                .settledClass(new SettledClass(teachingClass4, room3))
//                .settledClass(new SettledClass(teachingClass2, room3))
//                .settledClass(new SettledClass(teachingClass1, room3)).build())
//    );
//
//    FitnessFunction result = sut.fitness(chromosome, FitnessFunction.INITIAL);
//    assertThat(result).isEqualTo(FitnessFunction.INITIAL);
//  }
//}