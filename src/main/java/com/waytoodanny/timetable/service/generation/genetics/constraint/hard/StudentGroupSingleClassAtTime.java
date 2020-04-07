package com.waytoodanny.timetable.service.generation.genetics.constraint.hard;

import com.waytoodanny.timetable.service.generation.genetics.constraint.HardConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.Gene;
import org.springframework.stereotype.Component;

import static java.util.function.Predicate.not;

/**
 * No student group can attend more than one class at the same time.
 */
@Component
public class StudentGroupSingleClassAtTime implements HardConstraint {

  @Override
  public FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial) {
    return chromosome.getGenes().stream()
        .filter(not(Gene::isEachStudentGroupOccursOnce))
        .findAny()
        .map(g -> FitnessFunction.UNACCEPTABLE)
        .orElse(initial);
  }
}
