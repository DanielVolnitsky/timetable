//package com.waytoodanny.timetable.service.generation.genetics.impl;
//
//import com.waytoodanny.timetable.configuration.GeneticsProperties;
//import com.waytoodanny.timetable.configuration.UniversityProperties;
//import com.waytoodanny.timetable.domain.timetable.InputData;
//import com.waytoodanny.timetable.domain.university.*;
//import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
//import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
//import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
//import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
//import com.waytoodanny.timetable.service.generation.genetics.entity.SettledClass;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class RandomizedInitialPopulationTest {
//
//  GeneticsProperties geneticsProperties = new GeneticsProperties()
//      .setPopulationSize(3);
//
//  UniversityProperties universityProperties = new UniversityProperties()
//      .setAcademicHoursPerDay(3)
//      .setDaysPerWeek(5);
//
//  ScheduleConstraint constraintStub = (chromosome, initial) -> new FitnessFunction(1);
//
//  @Test
//  void population_positiveScenario() {
//    var sut = new RandomizedInitialPopulation(constraintStub, universityProperties, geneticsProperties);
//
//    var rooms = AvailableRooms.of(
//        new Room("1", 10),
//        new Room("2", 15),
//        new Room("3", 20));
//
//    var subject1 = new Subject("S1");
//    var subject2 = new Subject("S2");
//    var teacher1 = Teacher.builder().name("T1").build();
//    var teacher2 = Teacher.builder().name("T1").build();
//    var group1 = new StudentGroup("SG1", 11);
//    var group2 = new StudentGroup("SG2", 10);
//    var group3 = new StudentGroup("SG2", 16);
//    var class1 = TeachingClass.builder()
//        .subject(subject1)
//        .teacher(teacher1)
//        .group(group1)
//        .classesPerWeek(2)
//        .build();
//    var class2 = TeachingClass.builder()
//        .subject(subject2)
//        .teacher(teacher2)
//        .group(group2)
//        .classesPerWeek(1)
//        .build();
//    var class3 = TeachingClass.builder()
//        .subject(subject1)
//        .teacher(teacher1)
//        .group(group3)
//        .classesPerWeek(2)
//        .build();
//    var input = new InputData(Set.of(class1, class2, class3), rooms);
//
//    Population resultPopulation = sut.from(input);
//    assertThat(resultPopulation).isNotNull();
//
//    Chromosome[] resultChromosomes = resultPopulation.getChromosomes();
//    assertThat(resultChromosomes).isNotNull();
//    assertThat(resultChromosomes.length).isEqualTo(geneticsProperties.getPopulationSize());
//    Arrays.stream(resultChromosomes).forEach(c -> {
//      assertThatChromosomeHasCorrectGenesCount(c, rooms);
//      assertThatEachRoomIsAppropriateForAssignedClass(c);
//      assertThatChromosomeContainsTeachingClassExactly(c, class1, 2);
//      assertThatChromosomeContainsTeachingClassExactly(c, class2, 1);
//      assertThatChromosomeContainsTeachingClassExactly(c, class3, 2);
//    });
//  }
//
//  private void assertThatEachRoomIsAppropriateForAssignedClass(Chromosome c) {
//    c.getGenes()
//        .stream()
//        .flatMap(g -> g.getSettledClasses().stream())
//        .forEach(t -> assertThat(t.getTeachingClass().roomRequirements()).accepts(t.getRoom()));
//  }
//
//  private void assertThatChromosomeContainsTeachingClassExactly(Chromosome chromosome,
//                                                                TeachingClass tClass,
//                                                                int expectedEntries) {
//    long resultEntries = chromosome.getGenes().stream()
//        .flatMap(g -> g.getSettledClasses().stream())
//        .map(SettledClass::getTeachingClass)
//        .filter(tClass::equals)
//        .count();
//
//    assertThat(resultEntries).isEqualTo(expectedEntries);
//  }
//
//  private void assertThatChromosomeHasCorrectGenesCount(Chromosome chromosome, AvailableRooms rooms) {
//    int maxGenesPossible = universityProperties.getAcademicHoursPerDay() * rooms.size();
//    assertThat(maxGenesPossible)
//        .isGreaterThanOrEqualTo(chromosome.getGenes().size());
//  }
//}