package com.waytoodanny.timetable.event.listener;

import com.waytoodanny.timetable.domain.Parents;
import com.waytoodanny.timetable.domain.Population;
import com.waytoodanny.timetable.domain.chromosome.EvaluatedChromosome;
import com.waytoodanny.timetable.event.AlgorithmIteration;
import com.waytoodanny.timetable.event.CrossoverApplied;
import com.waytoodanny.timetable.event.ParentsSelected;
import com.waytoodanny.timetable.event.processing.AlgorithmIterationResultAggregator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@AllArgsConstructor
@Component
@Slf4j
public class GeneticAlgorithmLifecycleListener {

  private final AlgorithmIterationResultAggregator iterationResultAggregator;

  @EventListener
  public void handleAlgorithmIteration(AlgorithmIteration event) {
    int i = event.getIteration();
    Population p = event.getPopulation();

    Map<Integer, Long> groupedByFitness = p.stream()
        .collect(groupingBy(
            EvaluatedChromosome::fitnessValue,
            () -> new TreeMap<>(Comparator.reverseOrder()),
            counting()));

    Integer max = groupedByFitness.keySet().stream()
        .max(Comparator.comparingInt(f -> f)).orElse(0);

    long acceptableCount = p.stream().filter(EvaluatedChromosome::isAcceptable).count();

    log.info("Iteration {}\nPopulation stats: {}\nPopulation max fitness: {}\nAcceptable count: {}",
        i, groupedByFitness, max, acceptableCount);

    iterationResultAggregator.iterationResult(i, p);
  }

  @EventListener
  public void handleAlgorithmEnd(ContextClosedEvent event) {
    log.info("Best chromosome: {}", iterationResultAggregator.best());
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
//    logPopulationStatistics(event.getPopulation());
  }
}
