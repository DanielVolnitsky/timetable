package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.service.generation.genetics.Crossover;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Random;

@AllArgsConstructor
@Component
public class CrossoverImpl implements Crossover {

  private final GeneticsProperties geneticsProperties;
  private final Random random;

  @Override
  public Population apply(Collection<Parents> parents) {
    return null;
  }
}
