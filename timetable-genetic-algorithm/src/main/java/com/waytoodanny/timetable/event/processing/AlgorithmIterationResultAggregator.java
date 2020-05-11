package com.waytoodanny.timetable.event.processing;

import com.waytoodanny.timetable.domain.Population;
import com.waytoodanny.timetable.domain.chromosome.EvaluatedChromosome;

import java.util.Collection;

public interface AlgorithmIterationResultAggregator {

  void iterationResult(int iteration, Population population);

  //TODO
  Collection<EvaluatedChromosome> best();
}
