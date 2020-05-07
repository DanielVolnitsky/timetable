package com.waytoodanny.timetable.genetic.algorithm.impl;

import com.waytoodanny.timetable.genetic.GeneticsProperties;
import com.waytoodanny.timetable.genetic.algorithm.Mutation;
import com.waytoodanny.timetable.genetic.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.genetic.domain.GeneticInputData;
import com.waytoodanny.timetable.genetic.domain.GeneticTeachingClass;
import com.waytoodanny.timetable.genetic.domain.Population;
import com.waytoodanny.timetable.genetic.domain.chromosome.Chromosome;
import com.waytoodanny.timetable.genetic.domain.chromosome.EvaluatedChromosome;
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
  public Population apply(Population population, GeneticInputData data) {
    return new Population(
        population.stream()
            .map(c -> mutationResult(c, data))
            .collect(toList()));
  }

  private EvaluatedChromosome mutationResult(EvaluatedChromosome evaluatedChromosome,
                                             GeneticInputData data) {
    if (Math.random() > geneticsProperties.getMutationRate()) {
      return evaluatedChromosome;
    }
    return new EvaluatedChromosome(mutatedChromosome(evaluatedChromosome, data), scheduleConstraints);
  }

  private Chromosome mutatedChromosome(EvaluatedChromosome evaluatedChromosome,
                                       GeneticInputData data) {
    List<GeneticTeachingClass> classes = data.classesToScheduleForWeek();

    Chromosome chromosomeCopy;
    boolean successfullyMutated;
    do {
      chromosomeCopy = evaluatedChromosome.getChromosome().prototype();

      GeneticTeachingClass randomClass1;
      GeneticTeachingClass randomClass2;
      do {
        randomClass1 = classes.get(random.nextInt(classes.size()));
        randomClass2 = classes.get(random.nextInt(classes.size()));
      } while (randomClass1 == randomClass2);

      successfullyMutated = chromosomeCopy.reschedule(randomClass1, randomClass2);
    } while (!successfullyMutated);

    return chromosomeCopy;
  }
}
