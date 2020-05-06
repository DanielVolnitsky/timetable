package com.waytoodanny.algorithm.impl;

import com.waytoodanny.GeneticInputData;
import com.waytoodanny.GeneticsProperties;
import com.waytoodanny.UniversityProperties;
import com.waytoodanny.algorithm.PopulationSupplier;
import com.waytoodanny.constraint.ScheduleConstraint;
import com.waytoodanny.entity.Population;
import com.waytoodanny.entity.chromosome.Chromosome;
import com.waytoodanny.entity.chromosome.EvaluatedChromosome;
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
  public Population apply(GeneticInputData data) {
    Population.PopulationBuilder populationBld = Population.builder();
    for (int i = 0; i < geneticsConfiguration.getPopulationSize(); i++) {
      populationBld.chromosome(
          new EvaluatedChromosome(
              generatedChromosome(data), scheduleConstraints));
    }
    return populationBld.build();
  }

  private Chromosome generatedChromosome(GeneticInputData data) {
    var chromosome = new Chromosome(data.getInputData().getRooms(), universityProperties.weekTimeSlots());

    boolean fullyGenerated = data.classesToScheduleForWeek().stream()
        .map(chromosome::scheduleClassRandomly)
        .noneMatch(p -> p.equals(false));

    return fullyGenerated ? chromosome : generatedChromosome(data);
  }
}
