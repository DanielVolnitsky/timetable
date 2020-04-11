package com.waytoodanny.timetable.service.generation.genetics.factory.impl;

import com.waytoodanny.timetable.service.generation.genetics.NextGenerationParents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.factory.NextGenerationParentsProviderFactory;
import com.waytoodanny.timetable.service.generation.genetics.impl.parentsprovider.WheelOfFortune;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Value
@Component
public class WheelOfFortuneFactory implements NextGenerationParentsProviderFactory {

  Random random;

  @Override
  public NextGenerationParents apply(Population population) {
    return new WheelOfFortune(population, random);
  }
}
