package com.waytoodanny.constraint;

import com.waytoodanny.entity.chromosome.Chromosome;
import com.waytoodanny.entity.chromosome.FitnessFunction;
import lombok.NonNull;

public interface ScheduleConstraint {
  FitnessFunction fitness(@NonNull Chromosome chromosome,
                          @NonNull FitnessFunction initial);

}
