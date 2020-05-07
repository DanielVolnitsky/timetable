package com.waytoodanny.timetable.genetic.constraint.soft;

import com.waytoodanny.UniversityProperties;
import com.waytoodanny.timetable.genetic.constraint.SoftConstraint;
import com.waytoodanny.timetable.genetic.domain.FitnessFunction;
import com.waytoodanny.timetable.genetic.domain.GeneticTeachingClass;
import com.waytoodanny.timetable.genetic.domain.SettledClass;
import com.waytoodanny.timetable.genetic.domain.TimeSlots;
import com.waytoodanny.timetable.genetic.domain.chromosome.Chromosome;
import com.waytoodanny.timetable.genetic.domain.university.StudentGroup;
import io.vavr.Tuple2;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Value
@Component
public class NoGapsForStudentGroup implements SoftConstraint {

  UniversityProperties universityProperties;
  TimeSlots timeSlots;

  @Override
  public FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial) {
    Set<StudentGroup> allGroups = allStudentGroups(chromosome);
    int weightForSingleGroup = weightForSingleGroup(allGroups.size());

    return allGroups.stream()
        .map(group -> daysWithoutGap(group, chromosome))
        .map(daysWithoutGap -> fitnessValueForNoGapDays(weightForSingleGroup, daysWithoutGap))
        .reduce(Integer::sum)
        .map(initial::plus)
        .orElse(initial);
  }

  private Set<StudentGroup> allStudentGroups(Chromosome chromosome) {
    return chromosome.getScheduledClasses()
        .values().stream()
        .flatMap(Collection::stream)
        .map(SettledClass::getGeneticTeachingClass)
        .map(GeneticTeachingClass::involvedStudentGroups)
        .flatMap(Collection::stream)
        .collect(toSet());
  }

  private int daysWithoutGap(StudentGroup sg, Chromosome chromosome) {
    int result = universityProperties.getDaysPerWeek();

    List<Integer> groupSlots = studentGroupTimeSlots(sg, chromosome);
    for (int i = 0; i < groupSlots.size() - 1; i++) {
      Integer curr = groupSlots.get(i);
      Integer next = groupSlots.get(i + 1);
      if (timeSlots.relateToSameDay(curr, next) && isGapBetween(curr, next)) {
        result -= 1;
      }
    }

    return result;
  }

  private List<Integer> studentGroupTimeSlots(StudentGroup sg, Chromosome chromosome) {
    return chromosome.getScheduledClasses().entrySet().stream()
        .map(e -> new Tuple2<>(
            e.getKey(),
            e.getValue().stream()
                .map(SettledClass::getGeneticTeachingClass)
                .map(GeneticTeachingClass::involvedStudentGroups)
                .flatMap(Collection::stream)
                .collect(toSet()))
        ).filter(t -> t._2.contains(sg))
        .map(t -> t._1)
        .sorted()
        .collect(toList());
  }

  private int fitnessValueForNoGapDays(int weightForSingleGroup, Integer daysWithoutGap) {
    return daysWithoutGap * weightForSingleDay(weightForSingleGroup, universityProperties.getDaysPerWeek());
  }

  private int weightForSingleDay(int weightForSingleGroup, int daysCount) {
    return weightForSingleGroup / daysCount;
  }

  private int weightForSingleGroup(int groupsCount) {
    return this.weight() / groupsCount;
  }

  private boolean isGapBetween(Integer curr, Integer next) {
    return next - curr != 1;
  }
}
