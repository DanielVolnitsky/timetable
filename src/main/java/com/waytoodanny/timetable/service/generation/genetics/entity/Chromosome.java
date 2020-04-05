package com.waytoodanny.timetable.service.generation.genetics.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Chromosome {

  private final List<Gene> genes;

  @Setter
  @Accessors(chain = true)
  private FitnessFunction fitnessFunction;
}
