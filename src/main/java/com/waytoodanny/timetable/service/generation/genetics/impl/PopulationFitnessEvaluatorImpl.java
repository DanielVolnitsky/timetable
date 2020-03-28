package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.service.generation.genetics.PopulationFitnessEvaluator;
import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.EvaluatedPopulation;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.Value;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Value
@Component
public class PopulationFitnessEvaluatorImpl implements PopulationFitnessEvaluator {

  ScheduleConstraint constraint;

  @Override
  public EvaluatedPopulation apply(Population population) {
    return new EvaluatedPopulation(
        population.stream()
            .map(chromosome -> chromosome.setFitnessFunction(
                constraint.fitness(chromosome, FitnessFunction.minimal())))
            .collect(toList())
    );
  }
}
