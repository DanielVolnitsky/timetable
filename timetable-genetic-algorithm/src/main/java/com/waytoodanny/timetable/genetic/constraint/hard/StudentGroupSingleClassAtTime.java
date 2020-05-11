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

import static java.util.stream.Collectors.toList;

@Component
public class StudentGroupSingleClassAtTime implements HardConstraint {

  @Override
  public FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial) {
    boolean allMatch = chromosome.scheduledClasses()
        .stream()
        .allMatch(scheduledClasses -> scheduledClasses.match(this::noClashes));

    return allMatch ? initial.plus(this.weight()) : initial;
  }

  private boolean noClashes(List<SettledClass> classes) {
    List<StudentGroup> groups = classes.stream()
        .map(SettledClass::getGeneticTeachingClass)
        .map(GeneticTeachingClass::involvedStudentGroups)
        .flatMap(Collection::stream)
        .collect(toList());

    return new HashSet<>(groups).size() == groups.size();
  }
}
