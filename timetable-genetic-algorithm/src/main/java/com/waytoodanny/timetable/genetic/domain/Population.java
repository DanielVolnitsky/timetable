package com.waytoodanny.timetable.genetic.domain;

import com.waytoodanny.timetable.genetic.domain.chromosome.EvaluatedChromosome;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

  //TODO
  public List<EvaluatedChromosome> bestChromosomes() {
    Map<Integer, List<EvaluatedChromosome>> c = stream()
        .collect(Collectors.groupingBy(EvaluatedChromosome::fitnessValue));

    return c.get(
        c.keySet().stream().max(Comparator.comparingInt(e -> e)).orElse(0));
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
