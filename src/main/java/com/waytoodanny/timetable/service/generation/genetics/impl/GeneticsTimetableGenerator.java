package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.timetable.Timetable;
import com.waytoodanny.timetable.service.event.EventPublisher;
import com.waytoodanny.timetable.service.generation.TimetableGenerator;
import com.waytoodanny.timetable.service.generation.genetics.Crossover;
import com.waytoodanny.timetable.service.generation.genetics.Mutation;
import com.waytoodanny.timetable.service.generation.genetics.NextGenParentsSupplier;
import com.waytoodanny.timetable.service.generation.genetics.PopulationSupplier;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
import com.waytoodanny.timetable.service.generation.genetics.event.AlgorithmIteration;
import com.waytoodanny.timetable.service.generation.genetics.event.CrossoverApplied;
import com.waytoodanny.timetable.service.generation.genetics.event.ParentsSelected;
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
    Population initialPopulation = this.populationSupplier.apply(input);
    eventPublisher.publish(new AlgorithmIteration(1, initialPopulation));

    Population nextGen = initialPopulation;
    int iteration = 1;
    while (!nextGen.hasSolution()) {
      Collection<Parents> parents = nextGenParentsSupplier.apply(nextGen);
      eventPublisher.publish(new ParentsSelected(parents));

      Population crossoverResult = crossover.apply(parents, input);
      eventPublisher.publish(new CrossoverApplied(crossoverResult));

      List<EvaluatedChromosome> eliteChromosomes = eliteChromosomes(nextGen);
      nextGen = mutation.apply(crossoverResult, input);
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
