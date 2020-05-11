package com.waytoodanny.timetable.genetic.algorithm.impl.postprocessor;

import com.waytoodanny.timetable.domain.chromosome.EvaluatedChromosome;
import com.waytoodanny.timetable.genetic.algorithm.TimetablePostProcessor;
import org.springframework.stereotype.Component;

@Component
public class LecturePracticeSorter implements TimetablePostProcessor {

  @Override
  public EvaluatedChromosome apply(EvaluatedChromosome evaluatedChromosome) {
    //TODO impl.
    return evaluatedChromosome;
  }
}
