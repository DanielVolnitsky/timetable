package com.waytoodanny.timetable.service.generation.genetics.entity;

import lombok.Value;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Set of chromosomes which are made up of group of genes which satisfies the
 * given hard and soft constraints like number of periods required, timings, classrooms etc
 */
@Value
public class Population {
  private Chromosome[] chromosomes;

  public Stream<Chromosome> stream() {
    return Arrays.stream(chromosomes);
  }
}
