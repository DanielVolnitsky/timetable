package com.waytoodanny.timetable.service.generation.genetics.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Chromosome implements Iterable<Gene> {

  private final List<Gene> genes;

  @Setter
  @Accessors(chain = true)
  private FitnessFunction fitnessFunction;

  @Override
  public Iterator<Gene> iterator() {
    return genes.iterator();
  }
}
