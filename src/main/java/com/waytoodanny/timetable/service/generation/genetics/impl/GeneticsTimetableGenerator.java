package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.timetable.Timetable;
import com.waytoodanny.timetable.service.generation.TimetableGenerator;
import com.waytoodanny.timetable.service.generation.genetics.Crossover;
import com.waytoodanny.timetable.service.generation.genetics.Mutation;
import com.waytoodanny.timetable.service.generation.genetics.NextGenParentsSupplier;
import com.waytoodanny.timetable.service.generation.genetics.PopulationSupplier;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

/**
 * 1. Initialize starting population.
 * Population is presented by the set of chromosome. Each chromosome has a fitness value.
 * Each chromosome consists of genes.
 */
@RequiredArgsConstructor
@Slf4j
public class GeneticsTimetableGenerator implements TimetableGenerator {

  private final PopulationSupplier populationSupplier;
  private final NextGenParentsSupplier nextGenParentsSupplier;
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
    Population initialPopulation = this.populationSupplier.apply(input);
    onInitialPopulationGenerated.accept(initialPopulation);

    Population nextGen = initialPopulation;
    int iteration = 1;
    while (nextGen.highestFitnessValue() < 700 && iteration++ < 10000) {
      Collection<Parents> parents = nextGenParentsSupplier.apply(initialPopulation);

      nextGen = mutation.compose(crossover).apply(parents);
      nextGen = nextGen.addAll(eliteChromosomes(initialPopulation));

      onIterationPassed.accept(iteration, nextGen);
    }
    return null;
  }

  private List<EvaluatedChromosome> eliteChromosomes(Population initialPopulation) {
    return initialPopulation.stream()
        .sorted(Comparator.comparingInt(EvaluatedChromosome::fitnessValue))
        .limit(3)
        .collect(toList());
  }
}
