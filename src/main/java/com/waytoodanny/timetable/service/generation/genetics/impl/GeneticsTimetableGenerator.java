package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.timetable.Timetable;
import com.waytoodanny.timetable.service.generation.TimetableGenerator;
import com.waytoodanny.timetable.service.generation.genetics.Crossover;
import com.waytoodanny.timetable.service.generation.genetics.InitialPopulation;
import com.waytoodanny.timetable.service.generation.genetics.Mutation;
import com.waytoodanny.timetable.service.generation.genetics.NextGenParents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 1. Initialize starting population.
 * Population is presented by the set of chromosome. Each chromosome has a fitness value.
 * Each chromosome consists of genes.
 */
@RequiredArgsConstructor
@Slf4j
public class GeneticsTimetableGenerator implements TimetableGenerator {

  private final InitialPopulation initialPopulation;
  private final NextGenParents nextGenParents;
  private final Crossover crossover;
  private final Mutation mutation;

  private Consumer<Population> onInitialPopulationGenerated = p -> {
  };

  private BiConsumer<Integer, Population> onIterationPassed = (i, p) -> {
  };

  public GeneticsTimetableGenerator onInitialPopulationGenerated(Consumer<Population> hook) {
    this.onInitialPopulationGenerated = this.onInitialPopulationGenerated.andThen(hook);
    return this;
  }

  public GeneticsTimetableGenerator onIterationPassed(BiConsumer<Integer, Population> hook) {
    this.onIterationPassed = this.onIterationPassed.andThen(hook);
    return this;
  }

  @Override
  public Timetable timetable(InputData input) {
    Population initialPopulation = this.initialPopulation.from(input);
    onInitialPopulationGenerated.accept(initialPopulation);

//    Population result = initialPopulation;
//    int iteration = 1;
//    while (result.highestFitnessValue() < 700 && iteration++ < 10000) {
//      Collection<Parents> nextGenParents = this.nextGenParents.apply(initialPopulation);
//      //TODO add elite
//      result = mutation.compose(crossover).apply(nextGenParents);
//
//      onIterationPassed.accept(iteration, result);
//    }
    return null;
  }
}
