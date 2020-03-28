package com.waytoodanny.timetable.service.generation.genetics;

import com.waytoodanny.timetable.configuration.GeneticsConfiguration;
import com.waytoodanny.timetable.configuration.UniversityConfiguration;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.timetable.Timetable;
import com.waytoodanny.timetable.service.generation.TimetableGenerator;
import com.waytoodanny.timetable.service.generation.genetics.entity.EvaluatedPopulation;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.Value;
import org.springframework.stereotype.Component;

/**
 * 1. Initialize starting population.
 * Population is presented by the set of chromosome. Each chromosome has a fitness value.
 * Each chromosome consists of genes.
 */
@Value
@Component
public class GeneticsTimetableGenerator implements TimetableGenerator {

  private final PopulationProvider populationProvider;
  private final PopulationFitnessEvaluator fitnessEvaluator;
  private final UniversityConfiguration universityConfiguration;
  private final GeneticsConfiguration geneticsConfiguration;

  @Override
  public Timetable timetable(InputData input) {
    Population firstPopulation = populationProvider.population(input);
    EvaluatedPopulation evaluatedPopulation = fitnessEvaluator.apply(firstPopulation);

    return null;
  }
}
