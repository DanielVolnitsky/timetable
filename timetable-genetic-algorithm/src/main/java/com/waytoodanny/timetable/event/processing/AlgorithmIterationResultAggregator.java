package com.waytoodanny.timetable.event.processing;

import com.waytoodanny.timetable.genetic.domain.Population;
import com.waytoodanny.timetable.genetic.domain.chromosome.EvaluatedChromosome;

import java.util.Collection;

public interface AlgorithmIterationResultAggregator {

  void iterationResult(int iteration, Population population);

  //TODO
  Collection<EvaluatedChromosome> best();
}
