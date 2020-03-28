package com.waytoodanny.timetable.service.generation.genetics.constraint;

import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;

public interface ScheduleConstraint {
  FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial);
}
