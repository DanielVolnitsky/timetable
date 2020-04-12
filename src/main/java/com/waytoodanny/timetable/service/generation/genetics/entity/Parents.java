package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.Chromosome;
import lombok.Value;

import java.util.Objects;

@Value
public class Parents {
  Chromosome firstParent;
  Chromosome secondParent;

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
