//package com.waytoodanny.timetable.service.generation.genetics.constraint.hard;
//
//import domain.university.Room;
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
//class SingleClassInRoomAtTimeTest {
//
//  Room room1 = new Room("1", 10);
//  Room room2 = new Room("2", 10);
//  Room room3 = new Room("3", 10);
//  TeachingClass blankTeachingCLass = TeachingClass.builder().build();
//
//  SingleClassInRoomAtTime sut = new SingleClassInRoomAtTime();
//
//  @Test
//  void fitnessWhenAllGenesHaveUniqueRooms() {
//    var chromosome = new Chromosome(
//        List.of(
//            Gene.builder()
//                .timeSlot(2)
//                .settledClass(new SettledClass(blankTeachingCLass, room1)).build(),
//            Gene.builder()
//                .timeSlot(1)
//                .settledClass(new SettledClass(blankTeachingCLass, room1))
//                .settledClass(new SettledClass(blankTeachingCLass, room2)).build(),
//            Gene.builder()
//                .timeSlot(3)
//                .settledClass(new SettledClass(blankTeachingCLass, room1))
//                .settledClass(new SettledClass(blankTeachingCLass, room2))
//                .settledClass(new SettledClass(blankTeachingCLass, room3)).build())
//    );
//
//    FitnessFunction result = sut.fitness(chromosome, FitnessFunction.INITIAL);
//    assertThat(result).isEqualTo(FitnessFunction.INITIAL.plus(sut.weight()));
//  }
//
//  @Test
//  void fitnessWhenSomeGenesHaveUniqueRooms() {
//    var chromosome = new Chromosome(
//        List.of(
//            Gene.builder()
//                .timeSlot(2)
//                .settledClass(new SettledClass(blankTeachingCLass, room3)).build(),
//            Gene.builder()
//                .timeSlot(1)
//                .settledClass(new SettledClass(blankTeachingCLass, room1))
//                .settledClass(new SettledClass(blankTeachingCLass, room1))
//                .build(),
//            Gene.builder()
//                .timeSlot(3)
//                .settledClass(new SettledClass(blankTeachingCLass, room1))
//                .settledClass(new SettledClass(blankTeachingCLass, room2))
//                .build())
//    );
//
//    FitnessFunction result = sut.fitness(chromosome, FitnessFunction.INITIAL);
//    assertThat(result).isEqualTo(FitnessFunction.INITIAL);
//  }
//
//  @Test
//  void fitnessWhenNoGeneHasUniqueRooms() {
//    var chromosome = new Chromosome(
//        List.of(
//            Gene.builder()
//                .timeSlot(2)
//                .settledClass(new SettledClass(blankTeachingCLass, room1)).build(),
//            Gene.builder()
//                .timeSlot(1)
//                .settledClass(new SettledClass(blankTeachingCLass, room1))
//                .settledClass(new SettledClass(blankTeachingCLass, room1))
//                .build(),
//            Gene.builder()
//                .timeSlot(3)
//                .settledClass(new SettledClass(blankTeachingCLass, room1))
//                .settledClass(new SettledClass(blankTeachingCLass, room2))
//                .settledClass(new SettledClass(blankTeachingCLass, room1))
//                .build())
//    );
//
//    FitnessFunction result = sut.fitness(chromosome, FitnessFunction.INITIAL);
//    assertThat(result).isEqualTo(FitnessFunction.INITIAL);
//  }
//}