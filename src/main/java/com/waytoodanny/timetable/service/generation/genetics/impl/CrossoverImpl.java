package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.service.generation.genetics.Crossover;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Component
public class CrossoverImpl implements Crossover {

  private final GeneticsProperties geneticsProperties;
  private final Random random;

  @Override
  public Population apply(Collection<Parents> parents) {
    var result = parents.stream().map(this::cross).collect(toList());
    return new Population(result);
  }

  private Chromosome cross(Parents parents) {
    var p1 = parents.getFirstParent();
    var p2 = parents.getSecondParent();

    //For each class ought to be scheduled
    //get ts1 and ts2 - choose random and set Gene

    return p1.fitnessValue() > p2.fitnessValue() ? p1 : p2;
  }
}
