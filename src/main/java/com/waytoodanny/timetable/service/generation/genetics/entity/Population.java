package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
import lombok.Builder;
import lombok.Singular;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Set of chromosomes which are made up of group of genes which satisfies the
 * given hard and soft constraints like number of periods required, timings, classrooms etc
 */
@Builder
public class Population {

  @Singular
  List<EvaluatedChromosome> chromosomes;

  public Stream<EvaluatedChromosome> stream() {
    return chromosomes.stream();
  }

  public int highestFitnessValue() {
    return stream()
        .max(Comparator.comparingInt(EvaluatedChromosome::fitnessValue))
        .map(EvaluatedChromosome::fitnessValue)
        .orElse(0);
  }

  public int size() {
    return chromosomes.size();
  }

  public Population addAll(Collection<EvaluatedChromosome> chromosomes) {
    var c = new ArrayList<>(chromosomes);
    c.addAll(chromosomes);
    return new Population(c);
  }
}
