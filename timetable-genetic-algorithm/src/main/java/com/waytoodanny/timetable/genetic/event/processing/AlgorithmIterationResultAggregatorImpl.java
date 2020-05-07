package com.waytoodanny.timetable.genetic.event.processing;

import com.waytoodanny.timetable.genetic.domain.Population;
import com.waytoodanny.timetable.genetic.domain.chromosome.EvaluatedChromosome;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import static java.util.Collections.singletonList;

@Component
public class AlgorithmIterationResultAggregatorImpl
    implements AlgorithmIterationResultAggregator {

  private final TreeSet<EvaluatedChromosome> best =
      new TreeSet<>(Comparator.comparingInt(EvaluatedChromosome::fitnessValue));

  @Override
  public void iterationResult(int iteration, Population population) {
    best.addAll(population.bestChromosomes());
  }

  @Override
  public Collection<EvaluatedChromosome> best() {
    //TODO
    return singletonList(best.last());
  }
}
