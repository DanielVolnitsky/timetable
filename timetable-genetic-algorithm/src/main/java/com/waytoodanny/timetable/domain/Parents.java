package com.waytoodanny.timetable.domain;

import com.waytoodanny.timetable.domain.chromosome.Chromosome;
import com.waytoodanny.timetable.domain.chromosome.EvaluatedChromosome;
import lombok.Value;

import java.util.Objects;

@Value
public class Parents {
  EvaluatedChromosome first;
  EvaluatedChromosome second;

  public int overallFitness() {
    return first.fitnessValue() + second.fitnessValue();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Parents parents = (Parents) o;
    return (Objects.equals(first, parents.first) &&
        Objects.equals(second, parents.second))
        || (Objects.equals(first, parents.second) &&
        Objects.equals(second, parents.first));
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second);
  }

  //TODO
  public Chromosome opposite(Chromosome c) {
    return first.getChromosome().equals(c) ? second.getChromosome() : first.getChromosome();
  }
}
