package com.waytoodanny.timetable.service.generation.genetics.constraint.hard;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.Gene;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SingleClassInRoomAtTimeTest {

  Room room1 = new Room("1", 10);
  Room room2 = new Room("2", 10);
  Room room3 = new Room("3", 10);
  TeachingClass blankTeachingCLass = TeachingClass.builder().build();

  SingleClassInRoomAtTime sut = new SingleClassInRoomAtTime();

  @Test
  void fitnessWhenAllGenesHaveUniqueRooms() {
    var chromosome = new Chromosome(
        List.of(
            Gene.builder()
                .timeSlot(2)
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room1)).build(),
            Gene.builder()
                .timeSlot(1)
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room1))
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room2)).build(),
            Gene.builder()
                .timeSlot(3)
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room1))
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room2))
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room3)).build())
    );

    FitnessFunction result = sut.fitness(chromosome, FitnessFunction.minimal());
    assertThat(result).isEqualTo(new FitnessFunction(100));
  }

  @Test
  void fitnessWhenSomeGenesHaveUniqueRooms() {
    var chromosome = new Chromosome(
        List.of(
            Gene.builder()
                .timeSlot(2)
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room3)).build(),
            Gene.builder()
                .timeSlot(1)
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room1))
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room1))
                .build(),
            Gene.builder()
                .timeSlot(3)
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room1))
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room2))
                .build())
    );

    FitnessFunction result = sut.fitness(chromosome, FitnessFunction.minimal());
    assertThat(result).isEqualTo(FitnessFunction.unacceptable());
  }

  @Test
  void fitnessWhenNoGeneHasUniqueRooms() {
    var chromosome = new Chromosome(
        List.of(
            Gene.builder()
                .timeSlot(2)
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room1)).build(),
            Gene.builder()
                .timeSlot(1)
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room1))
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room1))
                .build(),
            Gene.builder()
                .timeSlot(3)
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room1))
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room2))
                .teachingTuple(new Gene.Tuple(blankTeachingCLass, room1))
                .build())
    );

    FitnessFunction result = sut.fitness(chromosome, FitnessFunction.minimal());
    assertThat(result).isEqualTo(FitnessFunction.unacceptable());
  }
}