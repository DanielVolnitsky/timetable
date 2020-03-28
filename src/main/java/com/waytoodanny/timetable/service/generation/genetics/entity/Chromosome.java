package com.waytoodanny.timetable.service.generation.genetics.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@EqualsAndHashCode
@ToString
public class Chromosome {

  @Getter
  private final List<Gene> genes;

  @Setter
  @Accessors(chain = true)
  private FitnessFunction fitnessFunction;

  public Optional<FitnessFunction> fitnessFunction() {
    return Optional.ofNullable(fitnessFunction);
  }
}
