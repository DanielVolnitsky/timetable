package com.waytoodanny.timetable.service.generation.genetics.entity;

import lombok.Value;

import java.util.Arrays;
import java.util.stream.Stream;

@Value
public class Population {
  private Chromosome[] chromosomes;

  public Stream<Chromosome> stream() {
    return Arrays.stream(chromosomes);
  }
}
