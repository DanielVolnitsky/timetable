package com.waytoodanny.timetable.genetic.algorithm.impl;

import com.waytoodanny.UniversityProperties;
import com.waytoodanny.timetable.genetic.GeneticsProperties;
import com.waytoodanny.timetable.genetic.algorithm.Crossover;
import com.waytoodanny.timetable.genetic.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.genetic.domain.GeneticInputData;
import com.waytoodanny.timetable.genetic.domain.GeneticTeachingClass;
import com.waytoodanny.timetable.genetic.domain.Parents;
import com.waytoodanny.timetable.genetic.domain.Population;
import com.waytoodanny.timetable.genetic.domain.chromosome.Chromosome;
import com.waytoodanny.timetable.genetic.domain.chromosome.EvaluatedChromosome;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Random;

@AllArgsConstructor
@Component
public class CrossoverImpl implements Crossover {

  private final Collection<ScheduleConstraint> constraints;
  private final UniversityProperties universityProperties;
  private final GeneticsProperties geneticsProperties;
  private final Random random;

  @Override
  public Population apply(Collection<Parents> parents, GeneticInputData data) {
    var result = Population.builder();
    parents.stream()
        .map(p -> crossover(p, data))
        .map(c -> new EvaluatedChromosome(c, constraints))
        .forEach(result::chromosome);
    return result.build();
  }

  //TODO refactor
  private Chromosome crossover(Parents parents, GeneticInputData data) {
    var p1 = parents.getFirst().getChromosome();
    var p2 = parents.getSecond().getChromosome();

    var result = new Chromosome(data.getInputData().getRooms(), universityProperties.weekTimeSlots());
    for (GeneticTeachingClass teachClass : data.classesToScheduleForWeek()) {
      Chromosome parentForClass = select(p1, p2);
      int classTimeslot = parentForClass.timeslotForClass(teachClass);
      boolean scheduled = result.scheduleClass(teachClass, classTimeslot);

      if (!scheduled) {
        Chromosome otherParent = parents.opposite(parentForClass);
        int classTimeslot2 = otherParent.timeslotForClass(teachClass);
        scheduled = result.scheduleClass(teachClass, classTimeslot2);

        if (!scheduled) {
          scheduled = result.scheduleClassRandomly(teachClass);

          if (!scheduled) {
            return crossover(parents, data);
          }
        }
      }
    }
    return result;
  }

  private Chromosome select(Chromosome c1, Chromosome c2) {
    return Math.random() > 0.5 ? c1 : c2;
  }
}
