package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.configuration.UniversityProperties;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.university.TeachingClass;
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
    var chr = new Chromosome(data.getRooms(), universityProperties.weekTimeSlots());
    data.classesToScheduleForWeek().forEach(cl -> scheduleClass(cl, chr));
    return chr;
  }

  private void scheduleClass(TeachingClass cl, Chromosome chr) {
    boolean done;
    do {
      int randomSlot = randomTimeSlot(universityProperties.timeSlotsPerWeek());
      done = chr.scheduleClass(cl, randomSlot);
    } while (!done);
  }

  private int randomTimeSlot(int totalSlots) {
    return random.nextInt(totalSlots) + 1;
  }
}