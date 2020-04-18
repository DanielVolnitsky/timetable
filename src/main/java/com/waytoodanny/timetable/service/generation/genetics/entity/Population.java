package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
import lombok.Builder;
import lombok.Singular;

import java.util.*;
import java.util.stream.Collectors;
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

  public boolean hasSolution() {
    return stream().anyMatch(EvaluatedChromosome::isAcceptable);
  }

  public int highestFitnessValue() {
    return stream()
        .max(Comparator.comparingInt(EvaluatedChromosome::fitnessValue))
        .map(EvaluatedChromosome::fitnessValue)
        .orElse(0);
  }

  //TODO
  public List<EvaluatedChromosome> bestChromosomes() {
    Map<Integer, List<EvaluatedChromosome>> c = stream()
        .collect(Collectors.groupingBy(EvaluatedChromosome::fitnessValue));

    return c.get(
        c.keySet().stream().max(Comparator.comparingInt(e -> e)).orElse(0));
  }

  public int size() {
    return chromosomes.size();
  }

  public Population addAll(Collection<EvaluatedChromosome> chromosomes) {
    var c = new ArrayList<>(chromosomes);
    c.addAll(this.chromosomes);
    return new Population(c);
  }
}
