package com.waytoodanny.algorithm.impl;

import com.waytoodanny.GeneticInputData;
import com.waytoodanny.GeneticsProperties;
import com.waytoodanny.algorithm.Crossover;
import com.waytoodanny.algorithm.Mutation;
import com.waytoodanny.algorithm.NextGenParentsSupplier;
import com.waytoodanny.algorithm.PopulationSupplier;
import com.waytoodanny.entity.Population;
import com.waytoodanny.entity.chromosome.EvaluatedChromosome;
import com.waytoodanny.entity.timetable.Parents;
import com.waytoodanny.event.AlgorithmIteration;
import com.waytoodanny.event.CrossoverApplied;
import com.waytoodanny.event.EventPublisher;
import com.waytoodanny.event.ParentsSelected;
import com.waytoodanny.timetable.InputData;
import com.waytoodanny.timetable.Timetable;
import com.waytoodanny.timetable.TimetableGenerator;
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
public class GeneticsTimetableGenerator implements TimetableGenerator {

  private final PopulationSupplier populationSupplier;
  private final NextGenParentsSupplier nextGenParentsSupplier;
  private final Crossover crossover;
  private final Mutation mutation;
  private final EventPublisher eventPublisher;
  private final GeneticsProperties geneticsProperties;

  @Override
  public Timetable timetable(InputData input) {
    GeneticInputData data = new GeneticInputData(input);

    Population initialPopulation = this.populationSupplier.apply(data);
    eventPublisher.publish(new AlgorithmIteration(1, initialPopulation));

    Population nextGen = initialPopulation;
    int iteration = 1;
//    while (!nextGen.hasSolution()) {
    while (nextGen.highestFitnessValue() != 1000) {
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
    //TODO convert to timetable
    return null;
  }

  private List<EvaluatedChromosome> eliteChromosomes(Population p) {
    return p.stream()
        .sorted(Comparator.comparingInt(EvaluatedChromosome::fitnessValue).reversed())
        .limit(geneticsProperties.getEliteChromosomesNumber())
        .collect(toList());
  }
}
