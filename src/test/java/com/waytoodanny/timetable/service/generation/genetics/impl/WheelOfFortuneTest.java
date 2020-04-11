package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static java.util.Collections.emptyList;

@ExtendWith(MockitoExtension.class)
class WheelOfFortuneTest {

  @Mock
  Random randomMock;

  @Test
  void getWhenTwoChromosomePresent() {
    //TODO
//    var chromosome1 = chromosomeWithFitness(10);
//    var chromosome2 = chromosomeWithFitness(8);
//    var source = new Population(new Chromosome[]{
//        chromosome1,
//        chromosome2
//    });
//
//    var sut = new WheelOfFortune(source, new Random());
//
//    Parents result = sut.get();
//    assertThat(result).isEqualTo(new Parents(chromosome1, chromosome2));
  }

  private Chromosome chromosomeWithFitness(int fitnessScore) {
    return new Chromosome(emptyList())
        .setFitnessFunction(new FitnessFunction(fitnessScore));
  }
}