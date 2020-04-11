package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.configuration.UniversityProperties;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.university.Rooms;
import com.waytoodanny.timetable.service.generation.genetics.InitialPopulation;
import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.*;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Value
@Component
public class RandomizedInitialPopulation implements InitialPopulation {

  ScheduleConstraint scheduleConstraint;
  UniversityProperties universityProperties;
  GeneticsProperties geneticsConfiguration;

  @Override
  public Population from(InputData input) {
    Chromosome[] chromosomes = Stream.generate(() -> randomChromosome(input))
        .map(c -> c.setFitnessFunction(scheduleConstraint.fitness(c, FitnessFunction.INITIAL)))
        .limit(geneticsConfiguration.populationSize())
        .toArray(Chromosome[]::new);

    return new Population(chromosomes);
  }

  private Chromosome randomChromosome(InputData input) {
    Rooms rooms = input.getRooms();
    int timeSlotsPerWeek = universityProperties.timeSlotsPerWeek();

    List<Gene> genes = input.classesToScheduleForWeek().stream()
        .collect(collectingAndThen(
            toMap(
                tClass -> randomTimeSlot(timeSlotsPerWeek),
                tClass -> Gene.builder()
                    .settledClass(SettledClass.withAnySuitableRoom(tClass, rooms)).build(),
                Gene::merge),
            map -> map.entrySet().stream()
                .map(e -> e.getValue().timeSlot(e.getKey()))
                .collect(toList())));

    return new Chromosome(genes);
  }

  //TODO
  private int randomTimeSlot(int totalSlots) {
    return (int) (Math.random() * totalSlots);
  }
}
