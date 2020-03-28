package com.waytoodanny.timetable.service.generation.genetics;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.configuration.UniversityProperties;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.timetable.Timetable;
import com.waytoodanny.timetable.service.generation.TimetableGenerator;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.EvaluatedPopulation;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
  private final UniversityProperties universityProperties;
  private final GeneticsProperties geneticsConfiguration;

  @Override
  public Timetable timetable(InputData input) {
    Population firstPopulation = populationProvider.population(input);
    EvaluatedPopulation evaluatedPopulation = fitnessEvaluator.apply(firstPopulation);

    Map<Boolean, List<Chromosome>> result = evaluatedPopulation.getEvaluatedChromosomes().stream()
        .filter(c -> c.fitnessFunction().isPresent())
        .collect(Collectors.partitioningBy(c -> c.fitnessFunction().get().equals(FitnessFunction.unacceptable())));

    long count = result.get(false).stream().filter(chromosome -> chromosome.getGenes().size() < 6).count();

    return null;
  }
}
