package com.waytoodanny.event.listener;

import com.waytoodanny.entity.Population;
import com.waytoodanny.entity.chromosome.EvaluatedChromosome;

import java.util.Collection;

public interface AlgorithmIterationResultAggregator {

  void iterationResult(int iteration, Population population);

  //TODO
  Collection<EvaluatedChromosome> best();
}
