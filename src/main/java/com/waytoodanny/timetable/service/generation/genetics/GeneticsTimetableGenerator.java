package com.waytoodanny.timetable.service.generation.genetics;

import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.timetable.Timetable;
import com.waytoodanny.timetable.service.generation.TimetableGenerator;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.factory.NextGenerationParentsProviderFactory;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

/**
 * 1. Initialize starting population.
 * Population is presented by the set of chromosome. Each chromosome has a fitness value.
 * Each chromosome consists of genes.
 */
@RequiredArgsConstructor
public class GeneticsTimetableGenerator implements TimetableGenerator {

  private final PopulationProvider populationProvider;
  private final NextGenerationParentsProviderFactory nextGenParentsProviderFactory;

  private Consumer<Population> onInitialPopulationGenerated = p -> {
  };

  public GeneticsTimetableGenerator onInitialPopulationGenerated(Consumer<Population> hook) {
    this.onInitialPopulationGenerated = this.onInitialPopulationGenerated.andThen(hook);
    return this;
  }

  @Override
  public Timetable timetable(InputData input) {
    Population initialPopulation = populationProvider.population(input);
    onInitialPopulationGenerated.accept(initialPopulation);

    NextGenerationParentsProvider parentsProvider = nextGenParentsProviderFactory.apply(initialPopulation);

    Parents parents = parentsProvider.get();

    return null;
  }
}
