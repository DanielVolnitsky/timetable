package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.configuration.UniversityProperties;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.university.Rooms;
import com.waytoodanny.timetable.service.generation.genetics.PopulationProvider;
import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.Gene;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

@Value
@Component
public class RandomizedPopulationProvider implements PopulationProvider {

  ScheduleConstraint scheduleConstraint;
  UniversityProperties universityProperties;
  GeneticsProperties geneticsConfiguration;

  @Override
  public Population population(InputData input) {
    Chromosome[] chromosomes = Stream.generate(() -> randomChromosome(input))
        .map(c -> c.setFitnessFunction(scheduleConstraint.fitness(c, FitnessFunction.INITIAL)))
        .filter(not(c -> c.getFitnessFunction().equals(FitnessFunction.UNACCEPTABLE)))
        .limit(geneticsConfiguration.getPopulationSize())
        .toArray(Chromosome[]::new);

    return new Population(chromosomes);
  }

  private Chromosome randomChromosome(InputData input) {
    Rooms rooms = input.getRooms();
    int timeSlotsPerWeek = universityProperties.timeSlotsPerWeek();

    List<Gene> genes = input.classesToScheduleForWeek().stream()
        .collect(collectingAndThen(
            toMap(
                c -> randomTimeSlot(timeSlotsPerWeek),
                c -> Gene.builder().teachingTuple(Gene.Tuple.withAnySuitableRoom(c, rooms)).build(),
                Gene::merge),
            map -> map.entrySet().stream()
                .map(e -> e.getValue().timeSlot(e.getKey()))
                .collect(toList())));

    return new Chromosome(genes);
  }

  private int randomTimeSlot(int totalSlots) {
    return (int) (Math.random() * totalSlots);
  }
}
