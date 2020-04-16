package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
import lombok.Value;

import java.util.Objects;

@Value
public class Parents {
  EvaluatedChromosome firstParent;
  EvaluatedChromosome secondParent;

  public int overallFitness() {
    return firstParent.fitnessValue() + secondParent.fitnessValue();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Parents parents = (Parents) o;
    return (Objects.equals(firstParent, parents.firstParent) &&
        Objects.equals(secondParent, parents.secondParent))
        || (Objects.equals(firstParent, parents.secondParent) &&
        Objects.equals(secondParent, parents.firstParent));
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstParent, secondParent);
  }
}
