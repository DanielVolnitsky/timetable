package com.waytoodanny.timetable.configuration;

import com.waytoodanny.timetable.service.generation.TimetableGenerator;
import com.waytoodanny.timetable.service.generation.genetics.Crossover;
import com.waytoodanny.timetable.service.generation.genetics.Mutation;
import com.waytoodanny.timetable.service.generation.genetics.NextGenParentsSupplier;
import com.waytoodanny.timetable.service.generation.genetics.PopulationSupplier;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
import com.waytoodanny.timetable.service.generation.genetics.impl.GeneticsTimetableGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.util.stream.Collectors.*;

@Configuration
@EnableConfigurationProperties(GeneticsProperties.class)
@Slf4j
public class GeneticsConfiguration {

  @Bean
  public TimetableGenerator timetableGenerator(PopulationSupplier pp,
                                               NextGenParentsSupplier ngp,
                                               Crossover crossover,
                                               Mutation mutation,
                                               Collection<Consumer<Population>> initPopulationHooks,
                                               Collection<BiConsumer<Integer, Population>> iterationHooks) {

    var gen = new GeneticsTimetableGenerator(pp, ngp, crossover, mutation);
    initPopulationHooks.forEach(gen::onInitialPopulationGenerated);
    iterationHooks.forEach(gen::onIterationPassed);
    return gen;
  }

  @Bean
  public Consumer<Population> logInitialPopulationHook() {
    return p -> {
      Map<Integer, Long> m = p.stream()
          .collect(groupingBy(EvaluatedChromosome::fitnessValue, TreeMap::new, counting()));

      log.info("Initial population stats: " + m);

      m.keySet().stream()
          .max(Comparator.comparingInt(f -> f))
          .ifPresent(max -> log.info("Initial population max fitness: " + max));

      Map<Integer, List<EvaluatedChromosome>> c = p.stream()
          .collect(groupingBy(EvaluatedChromosome::fitnessValue, toList()));

      log.info("Acceptable count: " + p.stream().filter(EvaluatedChromosome::isAcceptable).count());
    };
  }

  @Bean
  public BiConsumer<Integer, Population> logIterationResultsHook() {
    return (i, p) -> {
//      Map<Integer, Long> m = p.stream()
//          .collect(Collectors.groupingBy(Chromosome::fitnessValue, Collectors.counting()));
//
//      log.info("{} iteration population stats: {}", i, m);
//
//      m.keySet().stream()
//          .max(Comparator.comparingInt(f -> f))
//          .ifPresent(max -> log.info("{} iteration population max fitness: {}", i, max));
    };
  }

  @Bean
  public Random random() {
    return new Random();
  }
}
