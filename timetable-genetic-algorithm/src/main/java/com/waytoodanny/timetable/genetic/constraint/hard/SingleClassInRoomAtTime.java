package com.waytoodanny.timetable.genetic.constraint.hard;

import com.waytoodanny.timetable.domain.FitnessFunction;
import com.waytoodanny.timetable.domain.SettledClass;
import com.waytoodanny.timetable.domain.chromosome.Chromosome;
import com.waytoodanny.timetable.genetic.constraint.HardConstraint;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SingleClassInRoomAtTime implements HardConstraint {

  @Override
  public FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial) {
    boolean allMatch = chromosome.scheduledClasses()
        .stream()
        .allMatch(scheduledClasses -> scheduledClasses.match(this::noClashes));

    return allMatch ? initial.plus(this.weight()) : initial;
  }

  private boolean noClashes(List<SettledClass> classes) {
    var rooms = classes.stream()
        .map(SettledClass::getRoom)
        .collect(Collectors.toList());

    return new HashSet<>(rooms).size() == rooms.size();
  }
}
