package com.waytoodanny.timetable.genetic.algorithm.impl;

import com.waytoodanny.UniversityProperties;
import com.waytoodanny.timetable.domain.GeneticInputData;
import com.waytoodanny.timetable.domain.Population;
import com.waytoodanny.timetable.domain.chromosome.Chromosome;
import com.waytoodanny.timetable.domain.chromosome.ChromosomeFactory;
import com.waytoodanny.timetable.domain.chromosome.EvaluatedChromosome;
import com.waytoodanny.timetable.genetic.GeneticsProperties;
import com.waytoodanny.timetable.genetic.algorithm.InitialPopulationSupplier;
import com.waytoodanny.timetable.genetic.constraint.ScheduleConstraint;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Random;

@Value
@Component
public class RandomizedInitialPopulationSupplier implements InitialPopulationSupplier {

  ChromosomeFactory chromosomeFactory;
  Collection<ScheduleConstraint> scheduleConstraints;
  UniversityProperties universityProperties;
  GeneticsProperties geneticsConfiguration;
  Random random;

  @Override
  public Population apply(GeneticInputData data) {
    Population.PopulationBuilder builder = Population.builder();
    for (int i = 0; i < geneticsConfiguration.getPopulationSize(); i++) {
      builder.chromosome(
          new EvaluatedChromosome(
              generatedChromosome(data), scheduleConstraints));
    }
    return builder.build();
  }

  private Chromosome generatedChromosome(GeneticInputData data) {
    var chromosome = chromosomeFactory.chromosome(
        data.getInputData().getRooms(), universityProperties.weekTimeSlots());

    boolean fullyGenerated = data.classesToScheduleForWeek().stream()
        .map(chromosome::scheduleClassRandomly)
        .noneMatch(p -> p.equals(false));

    return fullyGenerated ? chromosome : generatedChromosome(data);
  }
}
