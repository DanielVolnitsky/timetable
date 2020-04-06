package com.waytoodanny.timetable.service.generation.genetics;

import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.timetable.Timetable;
import com.waytoodanny.timetable.service.generation.TimetableGenerator;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.factory.NextGenerationParentsProviderFactory;
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

  PopulationProvider populationProvider;
  NextGenerationParentsProviderFactory nextGenParentsProviderFactory;

  @Override
  public Timetable timetable(InputData input) {
    Population initialPopulation = populationProvider.population(input);
    NextGenerationParentsProvider parentsProvider = nextGenParentsProviderFactory.apply(initialPopulation);

    Parents parents = parentsProvider.get();

    return null;
  }
}
