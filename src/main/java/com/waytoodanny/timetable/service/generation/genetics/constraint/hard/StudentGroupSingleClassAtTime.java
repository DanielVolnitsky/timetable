package com.waytoodanny.timetable.service.generation.genetics.constraint.hard;

import com.waytoodanny.timetable.service.generation.genetics.constraint.HardConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;

/**
 * No student group can attend more than one class at the same time.
 */
public class StudentGroupSingleClassAtTime implements HardConstraint {

  @Override
  public FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial) {
    return null;
  }
}
