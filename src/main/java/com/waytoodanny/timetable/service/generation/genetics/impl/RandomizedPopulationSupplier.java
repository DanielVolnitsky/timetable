package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.configuration.UniversityProperties;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.service.generation.genetics.PopulationSupplier;
import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Random;

@Value
@Component
public class RandomizedPopulationSupplier implements PopulationSupplier {

  Collection<ScheduleConstraint> scheduleConstraints;
  UniversityProperties universityProperties;
  GeneticsProperties geneticsConfiguration;
  Random random;

  @Override
  public Population apply(InputData data) {
    Population.PopulationBuilder populationBld = Population.builder();
    for (int i = 0; i < geneticsConfiguration.getPopulationSize(); i++) {
      populationBld.chromosome(
          new EvaluatedChromosome(
              generatedChromosome(data), scheduleConstraints));
    }
    return populationBld.build();
  }

  private Chromosome generatedChromosome(InputData data) {
    var chromosome = new Chromosome(data.getRooms(), universityProperties.weekTimeSlots());

    boolean fullyGenerated = data.classesToScheduleForWeek().stream()
        .map(chromosome::scheduleClassRandomly)
        .noneMatch(p -> p.equals(false));

    return fullyGenerated ? chromosome : generatedChromosome(data);
  }
}
