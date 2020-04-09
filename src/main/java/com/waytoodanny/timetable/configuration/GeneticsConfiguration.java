package com.waytoodanny.timetable.configuration;

import com.waytoodanny.timetable.service.generation.genetics.constraint.HardConstraint;
import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.service.generation.genetics.constraint.SoftConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collection;
import java.util.Random;

@Configuration
@EnableConfigurationProperties(GeneticsProperties.class)
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

      //TODO temporary disabled. Restore afterwards
//      for (SoftConstraint soft : softConstraints) {
//        fitnessFunction = soft.fitness(chromosome, fitnessFunction);
//      }

      return fitnessFunction;
    };
  }

  @Bean
  public Random random() {
    return new Random();
  }
}
