package com.waytoodanny.timetable.service.generation.genetics.impl;

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
import com.waytoodanny.timetable.service.generation.genetics.event.ParentsSelected;
import com.waytoodanny.timetable.service.generation.genetics.event.PopulationGenerated;
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

  @Override
  public Timetable timetable(InputData input) {
    Population initialPopulation = this.populationSupplier.apply(input);
    eventPublisher.publish(new PopulationGenerated(initialPopulation));

    Population nextGen = initialPopulation;
    int iteration = 1;
    while (!nextGen.hasSolution() && iteration++ < 2) {
      Collection<Parents> parents = nextGenParentsSupplier.apply(initialPopulation);
      eventPublisher.publish(new ParentsSelected(parents));

//      nextGen = mutation.compose(crossover).apply(parents);
//      nextGen = nextGen.addAll(eliteChromosomes(initialPopulation));
//
//      eventPublisher.publish(new AlgorithmIteration(iteration, nextGen));
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
