package com.waytoodanny.timetable.service.generation.genetics.constraint;

import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.Chromosome;
import lombok.NonNull;

public interface ScheduleConstraint {
  FitnessFunction fitness(@NonNull Chromosome chromosome, @NonNull FitnessFunction initial);
}
