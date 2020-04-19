package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import com.waytoodanny.timetable.service.generation.genetics.Mutation;
import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Component
public class MutationImpl implements Mutation {

  private final GeneticsProperties geneticsProperties;
  private final Random random;
  private final Collection<ScheduleConstraint> scheduleConstraints;

  @Override
  public Population apply(Population population, InputData data) {
    return new Population(
        population.stream()
            .map(c -> mutate(c, data))
            .collect(toList()));
  }

  private EvaluatedChromosome mutate(EvaluatedChromosome evalCh, InputData data) {
    if (Math.random() > geneticsProperties.getMutationRate()) {
      return evalCh;
    }

    List<TeachingClass> classes = data.classesToScheduleForWeek();
    Chromosome chromosome = evalCh.getChromosome();

    TeachingClass randomClass1;
    TeachingClass randomClass2;
    do {
      randomClass1 = classes.get(random.nextInt(classes.size()));
      randomClass2 = classes.get(random.nextInt(classes.size()));
    } while (randomClass1 == randomClass2);

    chromosome.reschedule(randomClass1, randomClass2);

    return new EvaluatedChromosome(chromosome, scheduleConstraints);
  }
}
