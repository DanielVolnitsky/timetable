package com.waytoodanny.timetable.configuration;

import com.waytoodanny.timetable.service.generation.TimetableGenerator;
import com.waytoodanny.timetable.service.generation.genetics.GeneticsTimetableGenerator;
import com.waytoodanny.timetable.service.generation.genetics.PopulationProvider;
import com.waytoodanny.timetable.service.generation.genetics.constraint.HardConstraint;
import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.service.generation.genetics.constraint.SoftConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.factory.NextGenerationParentsProviderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(GeneticsProperties.class)
@Slf4j
public class GeneticsConfiguration {

  @Bean
  @Primary
  public ScheduleConstraint compositeScheduleConstraint(Collection<HardConstraint> hardConstraints,
                                                        Collection<SoftConstraint> softConstraints) {
    return (chromosome, initial) -> {

      FitnessFunction fitnessFunction = FitnessFunction.INITIAL;

      for (HardConstraint hard : hardConstraints) {
        FitnessFunction updatedFitness = hard.fitness(chromosome, fitnessFunction);
        if (updatedFitness.equals(FitnessFunction.UNACCEPTABLE)) {
          return updatedFitness;
        }
        fitnessFunction = updatedFitness;
      }

      for (SoftConstraint soft : softConstraints) {
        fitnessFunction = soft.fitness(chromosome, fitnessFunction);
      }

      return fitnessFunction;
    };
  }

  @Bean
  public TimetableGenerator timetableGenerator(PopulationProvider pp,
                                               NextGenerationParentsProviderFactory pf,
                                               Collection<Consumer<Population>> initPopulationHooks) {

    var gen = new GeneticsTimetableGenerator(pp, pf);
    initPopulationHooks.forEach(gen::onInitialPopulationGenerated);
    return gen;
  }

  @Bean
  public Consumer<Population> printHook() {
    return p -> {
      Map<Integer, Long> m = p.stream()
          .collect(Collectors.groupingBy(Chromosome::fitnessValue, Collectors.counting()));

      log.info("Initial population stats: " + m);

      m.keySet().stream()
          .max(Comparator.comparingInt(f -> f))
          .ifPresent(max -> log.info("Initial population max fitness: " + max));
    };
  }

  @Bean
  public Random random() {
    return new Random();
  }
}
