package com.waytoodanny.timetable.genetic.constraint;

import com.waytoodanny.timetable.domain.FitnessFunction;
import com.waytoodanny.timetable.domain.chromosome.Chromosome;
import lombok.NonNull;

public interface ScheduleConstraint {
  FitnessFunction fitness(@NonNull Chromosome chromosome,
                          @NonNull FitnessFunction initial);

}
