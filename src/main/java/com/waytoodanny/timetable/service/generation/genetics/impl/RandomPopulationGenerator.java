package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsConfiguration;
import com.waytoodanny.timetable.configuration.UniversityConfiguration;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.university.Rooms;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import com.waytoodanny.timetable.service.generation.genetics.PopulationGenerator;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.Gene;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Value
public class RandomPopulationGenerator implements PopulationGenerator {

  private final UniversityConfiguration universityConfiguration;
  private final GeneticsConfiguration geneticsConfiguration;

  @Override
  public Population population(InputData input) {
    Chromosome[] gen = new Chromosome[geneticsConfiguration.getPopulationSize()];
    for (int i = 0; i < gen.length; i++) {
      gen[i] = randomChromosome(input);
    }
    return new Population(gen);
  }

  private Chromosome randomChromosome(InputData input) {
    int timeSlotsPerWeek = universityConfiguration.timeSlotsPerWeek();
    Rooms rooms = input.getRooms();
    List<TeachingClass> classesForWeek = input.singleWeekClasses();

    Map<Integer, Gene.GeneBuilder> rawGenes = new HashMap<>();
    classesForWeek
        .forEach(c -> rawGenes.compute(
            randomTimeSlot(timeSlotsPerWeek),
            (slot, bld) -> (bld == null)
                ? Gene.builder().teachingTuple(new Gene.Tuple(c, rooms.suitableFor(c.roomRequirements()).orElseThrow()))
                : bld.teachingTuple(new Gene.Tuple(c, rooms.suitableFor(c.roomRequirements()).orElseThrow()))));

    List<Gene> genes = rawGenes.entrySet().stream()
        .map(e -> e.getValue().timeSlot(e.getKey()).build())
        .collect(toList());

    return new Chromosome(genes);
  }

  private int randomTimeSlot(int totalSlots) {
    return (int) (Math.random() * totalSlots);
  }
}
