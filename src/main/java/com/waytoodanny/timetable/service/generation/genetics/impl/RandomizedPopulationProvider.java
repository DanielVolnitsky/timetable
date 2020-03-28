package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsConfiguration;
import com.waytoodanny.timetable.configuration.UniversityConfiguration;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.university.Rooms;
import com.waytoodanny.timetable.service.generation.genetics.PopulationProvider;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.Gene;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Value
@Component
public class RandomizedPopulationProvider implements PopulationProvider {

  UniversityConfiguration universityConfiguration;
  GeneticsConfiguration geneticsConfiguration;

  @Override
  public Population population(InputData input) {
    Chromosome[] chromosomes = Stream.generate(() -> randomChromosome(input))
        .limit(geneticsConfiguration.getPopulationSize())
        .toArray(Chromosome[]::new);

    return new Population(chromosomes);
  }

  private Chromosome randomChromosome(InputData input) {
    Rooms rooms = input.getRooms();
    int timeSlotsPerWeek = universityConfiguration.timeSlotsPerWeek();

    //TODO benchmark
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

//TODO benchmark

//    Map<Integer, Gene.GeneBuilder> rawGenes = new HashMap<>();
//    input.classesToScheduleForWeek()
//        .forEach(c -> rawGenes.compute(
//            randomTimeSlot(timeSlotsPerWeek),
//            (slot, bld) -> (bld == null)
//                ? Gene.builder().teachingTuple(new Gene.Tuple(c, rooms.suitableFor(c.roomRequirements()).orElseThrow()))
//                : bld.teachingTuple(new Gene.Tuple(c, rooms.suitableFor(c.roomRequirements()).orElseThrow()))));
//
//    List<Gene> genes = rawGenes.entrySet().stream()
//        .map(e -> e.getValue().timeSlot(e.getKey()).build())
//        .collect(toList());
