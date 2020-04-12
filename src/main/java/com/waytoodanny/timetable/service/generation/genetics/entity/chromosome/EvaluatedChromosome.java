package com.waytoodanny.timetable.service.generation.genetics.entity.chromosome;

import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EvaluatedChromosome {

  private final Chromosome chromosome;
  private final Collection<ScheduleConstraint> constraints;
  @Getter
  private final Map<ScheduleConstraint, Integer> constraintsCorrespondence = new HashMap<>();

  private FitnessFunction fitnessFunction;

  public EvaluatedChromosome(Chromosome chromosome,
                             Collection<ScheduleConstraint> constraints) {
    this.chromosome = chromosome;
    this.constraints = constraints;
  }

  public FitnessFunction fitnessFunction() {
    if (Objects.nonNull(fitnessFunction)) {
      return fitnessFunction;
    }
    evaluateFitness();
    return fitnessFunction;
  }

  private void evaluateFitness() {
    var evaluated = FitnessFunction.INITIAL;
    for (ScheduleConstraint c : constraints) {
      FitnessFunction updated = c.fitness(chromosome, evaluated);
      constraintsCorrespondence.put(c, updated.getValue() - evaluated.getValue());
      evaluated = updated;
    }
    this.fitnessFunction = evaluated;
  }

  public int fitnessValue() {
    return fitnessFunction().getValue();
  }
}
