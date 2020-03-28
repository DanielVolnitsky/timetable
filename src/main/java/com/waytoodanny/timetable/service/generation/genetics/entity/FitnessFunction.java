package com.waytoodanny.timetable.service.generation.genetics.entity;

import lombok.Value;

/**
 * Tells how good a chromosome is, how it fits requirements
 */
@Value
public class FitnessFunction {
  int value;

  public static FitnessFunction minimal() {
    return new FitnessFunction(0);
  }

  public static FitnessFunction unacceptable() {
    return new FitnessFunction(Integer.MIN_VALUE);
  }

  public int fitness() {
    return 0;
  }

  public int normalizedFitness() {
    return 0;
  }

  public FitnessFunction plus(int value) {
    return new FitnessFunction(this.value + value);
  }
}
