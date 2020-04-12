package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.service.generation.genetics.Mutation;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@AllArgsConstructor
@Component
public class MutationImpl implements Mutation {

  private final GeneticsProperties geneticsProperties;
  private final Random random;

  @Override
  public Population apply(Population population) {
    return population;
  }

}
