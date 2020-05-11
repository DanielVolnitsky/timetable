package com.waytoodanny.timetable.genetic.constraint.hard;

import com.waytoodanny.timetable.domain.FitnessFunction;
import com.waytoodanny.timetable.domain.GeneticTeachingClass;
import com.waytoodanny.timetable.domain.SettledClass;
import com.waytoodanny.timetable.domain.chromosome.Chromosome;
import com.waytoodanny.timetable.domain.university.StudentGroup;
import com.waytoodanny.timetable.genetic.constraint.HardConstraint;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * No student group can attend more than one class at the same time.
 */
@Component
public class StudentGroupSingleClassAtTime implements HardConstraint {

  @Override
  public FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial) {
    return chromosome.getScheduledClasses()
        .values().stream()
        .map(Chromosome.ScheduledClasses::getClasses)
        .map(this::noClashes)
        .filter(noClashes -> !noClashes)
        .findAny()
        .map(clashes -> initial)
        .orElseGet(() -> initial.plus(this.weight()));
  }

  private boolean noClashes(List<SettledClass> classes) {
    List<StudentGroup> groups = classes.stream()
        .map(SettledClass::getGeneticTeachingClass)
        .map(GeneticTeachingClass::involvedStudentGroups)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());

    return new HashSet<>(groups).size() == groups.size();
  }
}
