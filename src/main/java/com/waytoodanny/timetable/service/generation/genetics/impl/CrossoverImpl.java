package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.configuration.UniversityProperties;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.university.teachingclass.TeachingClass;
import com.waytoodanny.timetable.service.generation.genetics.Crossover;
import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
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
  public Population apply(Collection<Parents> parents, InputData data) {
    var result = Population.builder();
    parents.stream()
        .map(p -> crossover(p, data))
        .map(c -> new EvaluatedChromosome(c, constraints))
        .forEach(result::chromosome);
    return result.build();
  }

  //TODO refactor
  private Chromosome crossover(Parents parents, InputData data) {
    var p1 = parents.getFirst().getChromosome();
    var p2 = parents.getSecond().getChromosome();

    var result = new Chromosome(data.getRooms(), universityProperties.weekTimeSlots());
    for (TeachingClass teachClass : data.classesToScheduleForWeek()) {
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
