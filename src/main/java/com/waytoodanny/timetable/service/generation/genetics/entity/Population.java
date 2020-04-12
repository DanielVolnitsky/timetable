package com.waytoodanny.timetable.service.generation.genetics.entity;

import lombok.Builder;
import lombok.Singular;

import java.util.List;

/**
 * Set of chromosomes which are made up of group of genes which satisfies the
 * given hard and soft constraints like number of periods required, timings, classrooms etc
 */
@Builder
public class Population {

  @Singular
  List<Chromosome> chromosomes;

//
//  public Chromosome get(int index) {
//    return chromosomes.iterator();
//  }
//
//  public int size() {
//    return chromosomes.length;
//  }
//
//  public Stream<Chromosome> stream() {
//    return Arrays.stream(chromosomes);
//  }
//
//  public int highestFitnessValue() {
//    return stream()
//        .max(Comparator.comparingInt(Chromosome::fitnessValue))
//        .map(Chromosome::fitnessValue)
//        .orElse(0);
//  }
//
//  @Override
//  public Iterator<Chromosome> iterator() {
//    return Arrays.asList(chromosomes).iterator();
//  }
}
