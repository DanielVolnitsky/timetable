package com.waytoodanny.timetable.service.generation.genetics.entity;

import lombok.Value;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Set of chromosomes which are made up of group of genes which satisfies the
 * given hard and soft constraints like number of periods required, timings, classrooms etc
 */
@Value
public class Population implements Iterable<Chromosome> {

  Chromosome[] chromosomes;

  public Population(Collection<Chromosome> chromosomes) {
    this.chromosomes = chromosomes.toArray(Chromosome[]::new);
  }

  public Chromosome get(int index) {
    return chromosomes[index];
  }

  public int size() {
    return chromosomes.length;
  }

  public Stream<Chromosome> stream() {
    return Arrays.stream(chromosomes);
  }

  public int highestFitnessValue() {
    return stream()
        .max(Comparator.comparingInt(Chromosome::fitnessValue))
        .map(Chromosome::fitnessValue)
        .orElse(0);
  }

  @Override
  public Iterator<Chromosome> iterator() {
    return Arrays.asList(chromosomes).iterator();
  }
}
