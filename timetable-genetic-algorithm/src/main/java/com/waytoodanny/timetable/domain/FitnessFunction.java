package com.waytoodanny.timetable.domain;

import lombok.Value;

/**
 * Determines the fitness value of each chromosome and the chromosomes
 * with the highest fitness value is selected for crossover process.
 */
@Value
public class FitnessFunction {

  public static final FitnessFunction UNACCEPTABLE = new FitnessFunction(Integer.MIN_VALUE);
  public static final FitnessFunction INITIAL = new FitnessFunction(0);

  int value;

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
