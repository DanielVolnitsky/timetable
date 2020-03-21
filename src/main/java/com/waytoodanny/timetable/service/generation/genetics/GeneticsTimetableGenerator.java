package com.waytoodanny.timetable.service.generation.genetics;

import com.waytoodanny.timetable.configuration.GeneticsConfiguration;
import com.waytoodanny.timetable.configuration.UniversityConfiguration;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.timetable.Timetable;
import com.waytoodanny.timetable.service.generation.GenerateTimetableUseCase;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.Value;

/**
 * 1. Initialize starting population.
 * Population is presented by the set of chromosome. Each chromosome has a fitness value.
 * Each chromosome consists of genes.
 */
@Value
public class GeneticsTimetableGenerator implements GenerateTimetableUseCase {

  private final PopulationGenerator populationGenerator;
  private final UniversityConfiguration universityConfiguration;
  private final GeneticsConfiguration geneticsConfiguration;

  @Override
  public Timetable timetable(InputData input) {
    int periodsPerWeek = universityConfiguration.totalClassesPerWeek();

    Population firstGen = populationGenerator.population(input);
    return null;
  }
}
