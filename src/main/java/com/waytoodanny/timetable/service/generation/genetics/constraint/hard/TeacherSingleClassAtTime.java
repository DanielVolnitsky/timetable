package com.waytoodanny.timetable.service.generation.genetics.constraint.hard;

import com.waytoodanny.timetable.service.generation.genetics.constraint.HardConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.Gene;
import org.springframework.stereotype.Component;

import static java.util.function.Predicate.not;

@Component
public class TeacherSingleClassAtTime implements HardConstraint {

  @Override
  public FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial) {
    return chromosome.getGenes().stream()
        .filter(not(Gene::isEachTeacherOccursOnce))
        .findAny()
        .map(g -> FitnessFunction.UNACCEPTABLE)
        .orElse(initial);
  }
}
