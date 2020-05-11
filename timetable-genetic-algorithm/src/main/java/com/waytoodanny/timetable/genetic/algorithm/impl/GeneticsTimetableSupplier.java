package com.waytoodanny.timetable.genetic.algorithm.impl;

import com.waytoodanny.timetable.InputData;
import com.waytoodanny.timetable.Timetable;
import com.waytoodanny.timetable.TimetableSupplier;
import com.waytoodanny.timetable.domain.GeneticInputData;
import com.waytoodanny.timetable.domain.Parents;
import com.waytoodanny.timetable.domain.Population;
import com.waytoodanny.timetable.domain.chromosome.EvaluatedChromosome;
import com.waytoodanny.timetable.event.AlgorithmIteration;
import com.waytoodanny.timetable.event.CrossoverApplied;
import com.waytoodanny.timetable.event.EventPublisher;
import com.waytoodanny.timetable.event.ParentsSelected;
import com.waytoodanny.timetable.genetic.GeneticsProperties;
import com.waytoodanny.timetable.genetic.algorithm.Crossover;
import com.waytoodanny.timetable.genetic.algorithm.InitialPopulationSupplier;
import com.waytoodanny.timetable.genetic.algorithm.Mutation;
import com.waytoodanny.timetable.genetic.algorithm.NextGenParentsSupplier;
import com.waytoodanny.timetable.genetic.algorithm.PopulationTimetableConverter;
import com.waytoodanny.timetable.genetic.algorithm.TimetablePostProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 1. Initialize starting population.
 * Population is presented by the set of chromosome. Each chromosome has a fitness value.
 * Each chromosome consists of genes.
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class GeneticsTimetableSupplier implements TimetableSupplier {

  private final InitialPopulationSupplier initialPopulationSupplier;
  private final NextGenParentsSupplier nextGenParentsSupplier;
  private final Crossover crossover;
  private final Mutation mutation;
  private final GeneticsProperties geneticsProperties;
  private final EventPublisher eventPublisher;
  private final TimetablePostProcessor timetablePostProcessor;
  private final PopulationTimetableConverter timetableConverter;

  @Override
  public Timetable timetable(InputData input) {
    GeneticInputData data = new GeneticInputData(input);

    Population initialPopulation = this.initialPopulationSupplier.apply(data);
    eventPublisher.publish(new AlgorithmIteration(1, initialPopulation));

    Population nextGen = initialPopulation;
    int iteration = 1;

    while (!nextGen.hasSolution()) {
//    while (nextGen.highestFitnessValue() != 1000) {
      Collection<Parents> parents = nextGenParentsSupplier.apply(nextGen);
      eventPublisher.publish(new ParentsSelected(parents));

      Population crossoverResult = crossover.apply(parents, data);
      eventPublisher.publish(new CrossoverApplied(crossoverResult));

      List<EvaluatedChromosome> eliteChromosomes = eliteChromosomes(nextGen);
      nextGen = mutation.apply(crossoverResult, data);
      nextGen = nextGen.addAll(eliteChromosomes);

      eventPublisher.publish(new AlgorithmIteration(iteration, nextGen));
      iteration++;
    }

    EvaluatedChromosome result = timetablePostProcessor.apply(nextGen.bestChromosome());
    return timetableConverter.convert(result);
  }

  private List<EvaluatedChromosome> eliteChromosomes(Population p) {
    return p.stream()
        .sorted(Comparator.comparingInt(EvaluatedChromosome::fitnessValue).reversed())
        .limit(geneticsProperties.getEliteChromosomesNumber())
        .collect(toList());
  }
}
