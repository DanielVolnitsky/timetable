package com.waytoodanny.timetable.service.generation.genetics.event.listener;

import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
import com.waytoodanny.timetable.service.generation.genetics.event.AlgorithmIteration;
import com.waytoodanny.timetable.service.generation.genetics.event.CrossoverApplied;
import com.waytoodanny.timetable.service.generation.genetics.event.ParentsSelected;
import com.waytoodanny.timetable.service.generation.genetics.event.PopulationGenerated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Component
@Slf4j
public class GeneticAlgorithmLifecycleListener {

  @EventListener
  public void handlePopulationGenerated(PopulationGenerated event) {
    logPopulationStatistics(event.getPopulation());
  }

  @EventListener
  public void handleParentsSelected(ParentsSelected event) {
    Collection<Parents> parents = event.getParents();

    Map<Integer, Long> fitnessStatistics = Stream
        .concat(
            parents.stream().map(Parents::getFirst),
            parents.stream().map(Parents::getSecond))
        .collect(
            groupingBy(
                EvaluatedChromosome::fitnessValue,
                () -> new TreeMap<>(Comparator.reverseOrder()),
                counting()));

    log.info("Parents statistics: " + fitnessStatistics);
  }

  @EventListener
  public void handleCrossoverApplied(CrossoverApplied event) {
    logPopulationStatistics(event.getPopulation());
  }

  @EventListener
  public void handleAlgorithmIteration(AlgorithmIteration event) {
    Map<Integer, Long> groupedByFitness = event.getPopulation().stream()
        .collect(groupingBy(EvaluatedChromosome::fitnessValue, Collectors.counting()));

    int i = event.getIteration();
    log.info("{} iteration population stats: {}", i, groupedByFitness);

    groupedByFitness.keySet().stream()
        .max(Comparator.comparingInt(f -> f))
        .ifPresent(max -> log.info("{} iteration population max fitness: {}", i, max));
  }

  private void logPopulationStatistics(Population p) {
    Map<Integer, Long> groupedByFitness = p.stream()
        .collect(groupingBy(
            EvaluatedChromosome::fitnessValue,
            () -> new TreeMap<>(Comparator.reverseOrder()),
            counting()));

    log.info("Population stats: " + groupedByFitness);

    groupedByFitness.keySet().stream()
        .max(Comparator.comparingInt(f -> f))
        .ifPresent(max -> log.info("Population max fitness: " + max));

    log.info("Acceptable count: " + p.stream().filter(EvaluatedChromosome::isAcceptable).count());
  }
}
