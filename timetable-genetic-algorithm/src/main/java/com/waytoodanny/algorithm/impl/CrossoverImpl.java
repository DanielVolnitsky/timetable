package com.waytoodanny.algorithm.impl;

import com.waytoodanny.GeneticInputData;
import com.waytoodanny.GeneticsProperties;
import com.waytoodanny.UniversityProperties;
import com.waytoodanny.algorithm.Crossover;
import com.waytoodanny.constraint.ScheduleConstraint;
import com.waytoodanny.entity.GeneticTeachingClass;
import com.waytoodanny.entity.Population;
import com.waytoodanny.entity.chromosome.Chromosome;
import com.waytoodanny.entity.chromosome.EvaluatedChromosome;
import com.waytoodanny.entity.timetable.Parents;
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
