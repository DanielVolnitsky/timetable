package com.waytoodanny.timetable.genetic.constraint.hard;

import com.waytoodanny.timetable.genetic.constraint.HardConstraint;
import com.waytoodanny.timetable.genetic.domain.FitnessFunction;
import com.waytoodanny.timetable.genetic.domain.SettledClass;
import com.waytoodanny.timetable.genetic.domain.chromosome.Chromosome;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SingleClassInRoomAtTime implements HardConstraint {

  @Override
  public FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial) {
    return chromosome.getScheduledClasses()
        .values().stream()
        .map(this::noClashes)
        .filter(noClashes -> !noClashes)
        .findAny()
        .map(clashes -> initial)
        .orElseGet(() -> initial.plus(this.weight()));
  }

  private boolean noClashes(List<SettledClass> classes) {
    var rooms = classes.stream()
        .map(SettledClass::getRoom)
        .collect(Collectors.toList());

    return new HashSet<>(rooms).size() == rooms.size();
  }
}
