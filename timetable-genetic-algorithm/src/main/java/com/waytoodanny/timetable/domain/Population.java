package com.waytoodanny.timetable.domain;

import com.waytoodanny.timetable.domain.chromosome.EvaluatedChromosome;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * Set of chromosomes which are made up of group of genes which satisfies the
 * given hard and soft constraints like number of periods required, timings, classrooms etc
 */
@Builder
@AllArgsConstructor
public class Population {

  @Singular
  List<EvaluatedChromosome> chromosomes;

  public Stream<EvaluatedChromosome> stream() {
    return chromosomes.stream();
  }

  public boolean hasSolution() {
    return stream().anyMatch(EvaluatedChromosome::isAcceptable);
  }

  public List<EvaluatedChromosome> bestChromosomes() {
    Map<Integer, List<EvaluatedChromosome>> groupedByFitness = stream()
        .collect(groupingBy(EvaluatedChromosome::fitnessValue));

    Integer highestFitness = groupedByFitness.keySet().stream()
        .max(Comparator.comparingInt(e -> e))
        .orElseThrow();

    return groupedByFitness.get(highestFitness);
  }

  public EvaluatedChromosome bestChromosome() {
    return bestChromosomes().get(0);
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

  public EvaluatedChromosome get(int index) {
    return chromosomes.get(index);
  }

  public Population addAll(Collection<EvaluatedChromosome> chromosomes) {
    var c = new ArrayList<>(chromosomes);
    c.addAll(this.chromosomes);
    return new Population(c);
  }
}
