package com.waytoodanny.timetable.service.generation.genetics.constraint.soft;

import com.waytoodanny.timetable.configuration.UniversityProperties;
import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.StudentGroup;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.Gene;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NoGapsForStudentGroupTest {

  Room room = new Room("1", 10);

  StudentGroup sg1 = new StudentGroup("sg1", 10);
  StudentGroup sg2 = new StudentGroup("sg2", 10);

  TeachingClass class1 = TeachingClass.builder().group(sg1).build();
  TeachingClass class2 = TeachingClass.builder().group(sg2).build();

  UniversityProperties universityProperties = new UniversityProperties()
      .setAcademicHoursPerDay(3)
      .setDaysPerWeek(1);

  NoGapsForStudentGroup sut = new NoGapsForStudentGroup(new TimeSlots(universityProperties), universityProperties);

  @Test
  void fitnessWhenAllGroupsHaveNoGaps() {
    var chromosome = new Chromosome(
        List.of(
            Gene.builder()
                .timeSlot(1)
                .teachingTuple(new Gene.Tuple(class1, room)).build(),
            Gene.builder()
                .timeSlot(2)
                .teachingTuple(new Gene.Tuple(class2, room))
                .teachingTuple(new Gene.Tuple(class1, room)).build(),
            Gene.builder()
                .timeSlot(3)
                .teachingTuple(new Gene.Tuple(class2, room))
                .teachingTuple(new Gene.Tuple(class1, room)).build()));

    var expected = FitnessFunction.INITIAL.plus(sut.weight());
    var result = sut.fitness(chromosome, FitnessFunction.INITIAL);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void fitnessWhenOnlyOneGroupHasNoGaps() {
    var chromosome = new Chromosome(
        List.of(
            Gene.builder()
                .timeSlot(1)
                .teachingTuple(new Gene.Tuple(class1, room)).build(),
            Gene.builder()
                .timeSlot(2)
                .teachingTuple(new Gene.Tuple(class2, room)).build(),
            Gene.builder()
                .timeSlot(3)
                .teachingTuple(new Gene.Tuple(class2, room))
                .teachingTuple(new Gene.Tuple(class1, room)).build()));

    var expected = FitnessFunction.INITIAL.plus(sut.weight() / 2);
    var result = sut.fitness(chromosome, FitnessFunction.INITIAL);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void fitnessWhenAllGroupsHaveGaps() {
    var chromosome = new Chromosome(
        List.of(
            Gene.builder()
                .timeSlot(1)
                .teachingTuple(new Gene.Tuple(class1, room))
                .teachingTuple(new Gene.Tuple(class2, room)).build(),
            Gene.builder()
                .timeSlot(2).build(),
            Gene.builder()
                .timeSlot(3)
                .teachingTuple(new Gene.Tuple(class2, room))
                .teachingTuple(new Gene.Tuple(class1, room)).build()));

    var result = sut.fitness(chromosome, FitnessFunction.INITIAL);
    assertThat(result).isEqualTo(FitnessFunction.INITIAL);
  }
}