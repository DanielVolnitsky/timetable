package com.waytoodanny.timetable.service.generation.genetics.event.listener;

import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;

import java.util.Collection;

public interface AlgorithmIterationResultAggregator {

  void iterationResult(int iteration, Population population);

  Collection<EvaluatedChromosome> best();
}
