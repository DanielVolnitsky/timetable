package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
import lombok.Builder;
import lombok.Singular;

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

//
//  public Chromosome get(int index) {
//    return chromosomes.iterator();
//  }
//
//  public int size() {
//    return chromosomes.length;

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
