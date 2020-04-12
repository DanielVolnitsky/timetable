package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.configuration.UniversityProperties;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import com.waytoodanny.timetable.service.generation.genetics.InitialPopulation;
import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Value
@Component
public class RandomizedInitialPopulation implements InitialPopulation {

  ScheduleConstraint scheduleConstraint;
  UniversityProperties universityProperties;
  GeneticsProperties geneticsConfiguration;
  Random random;

  @Override
  public Population from(InputData data) {
    Population.PopulationBuilder populationBld = Population.builder();
    for (int i = 0; i < geneticsConfiguration.getPopulationSize(); i++) {
      populationBld.chromosome(generatedChromosome(data));
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
