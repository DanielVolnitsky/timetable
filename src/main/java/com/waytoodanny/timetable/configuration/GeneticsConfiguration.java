package com.waytoodanny.timetable.configuration;

import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collection;

@Configuration
@EnableConfigurationProperties(GeneticsProperties.class)
public class GeneticsConfiguration {

  @Bean
  @Primary
  public ScheduleConstraint compositeScheduleConstraint(Collection<ScheduleConstraint> constraints) {
    return (chromosome, initial) -> {
      FitnessFunction fitnessFunction = FitnessFunction.minimal();
      for (ScheduleConstraint constraint : constraints) {
        FitnessFunction updatedFitness = constraint.fitness(chromosome, fitnessFunction);
        if (updatedFitness.equals(FitnessFunction.unacceptable())) {
          return updatedFitness;
        }
        fitnessFunction = updatedFitness;
      }
      return fitnessFunction;
    };
  }
}
