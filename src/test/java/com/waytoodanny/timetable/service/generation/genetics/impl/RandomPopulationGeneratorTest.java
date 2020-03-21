package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsConfiguration;
import com.waytoodanny.timetable.configuration.UniversityConfiguration;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.university.*;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.Gene;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RandomPopulationGeneratorTest {

  private GeneticsConfiguration geneticsConfig = new GeneticsConfiguration()
      .setPopulationSize(3);

  private UniversityConfiguration univConfig = new UniversityConfiguration()
      .setAcademicHoursPerDay(3)
      .setDaysPerWeek(5);

  @Test
  void population_positiveScenario() {


    var sut = new RandomPopulationGenerator(univConfig, geneticsConfig);

    var rooms = Rooms.of(
        new Room("1", 10),
        new Room("2", 15),
        new Room("3", 20));

    var subject1 = new Subject("S1");
    var subject2 = new Subject("S2");
    var teacher1 = Teacher.builder().name("T1").build();
    var teacher2 = Teacher.builder().name("T1").build();
    var group1 = new StudentGroup("SG1", 11);
    var group2 = new StudentGroup("SG1", 10);
    var group3 = new StudentGroup("SG1", 16);
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
        .classesPerWeek(1)
        .build();
    var class3 = TeachingClass.builder()
        .subject(subject1)
        .teacher(teacher1)
        .group(group3)
        .classesPerWeek(2)
        .build();
    var input = new InputData(Set.of(class1, class2, class3), rooms);

    Population resultPopulation = sut.population(input);
    assertThat(resultPopulation).isNotNull();

    Chromosome[] resultChromosomes = resultPopulation.getChromosomes();
    assertThat(resultChromosomes).isNotNull();
    Arrays.stream(resultChromosomes).forEach(c -> {
      assertThatChromosomeHasCorrectGenesCount(c, rooms);
      assertThatChromosomeContainsTeachingClassExactly(c, class1, 2);
      assertThatChromosomeContainsTeachingClassExactly(c, class2, 1);
      assertThatChromosomeContainsTeachingClassExactly(c, class3, 2);
    });
  }

  private void assertThatChromosomeContainsTeachingClassExactly(Chromosome chromosome,
                                                                TeachingClass tClass,
                                                                int expectedEntries) {
    long resultEntries = chromosome.getGenes().stream()
        .flatMap(g -> g.getTeachingTuples().stream())
        .map(Gene.Tuple::getTeachingClass)
        .filter(tClass::equals)
        .count();

    assertThat(resultEntries).isEqualTo(expectedEntries);
  }

  private void assertThatChromosomeHasCorrectGenesCount(Chromosome chromosome, Rooms rooms) {
    int maxGenesPossible = univConfig.getAcademicHoursPerDay() * rooms.size();
    assertThat(maxGenesPossible)
        .isGreaterThanOrEqualTo(chromosome.getGenes().size());
  }
}