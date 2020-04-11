package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.service.generation.genetics.Crossover;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CrossoverImpl implements Crossover {

  @Override
  public Population apply(Collection<Parents> parents) {
    return null;
  }
}
