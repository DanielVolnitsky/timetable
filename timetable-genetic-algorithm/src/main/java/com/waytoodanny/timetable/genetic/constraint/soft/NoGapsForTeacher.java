package com.waytoodanny.timetable.genetic.constraint.soft;

import com.waytoodanny.UniversityProperties;
import com.waytoodanny.timetable.genetic.constraint.SoftConstraint;
import com.waytoodanny.timetable.genetic.domain.FitnessFunction;
import com.waytoodanny.timetable.genetic.domain.GeneticTeachingClass;
import com.waytoodanny.timetable.genetic.domain.SettledClass;
import com.waytoodanny.timetable.genetic.domain.TimeSlots;
import com.waytoodanny.timetable.genetic.domain.chromosome.Chromosome;
import com.waytoodanny.timetable.genetic.domain.university.Teacher;
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
public class NoGapsForTeacher implements SoftConstraint {

  UniversityProperties universityProperties;
  TimeSlots timeSlots;

  @Override
  public FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial) {
    Set<Teacher> allTeachers = allTeachers(chromosome);
    int weightForSingleTeacher = weightForSingleTeacher(allTeachers.size());

    return allTeachers.stream()
        .map(group -> daysWithoutGap(group, chromosome))
        .map(daysWithoutGap -> fitnessValueForNoGapDays(weightForSingleTeacher, daysWithoutGap))
        .reduce(Integer::sum)
        .map(initial::plus)
        .orElse(initial);
  }

  private Set<Teacher> allTeachers(Chromosome chromosome) {
    return chromosome.getScheduledClasses()
        .values().stream()
        .flatMap(Collection::stream)
        .map(SettledClass::getTeachingClass)
        .map(GeneticTeachingClass::involvedTeachers)
        .flatMap(Collection::stream)
        .collect(toSet());
  }

  private int daysWithoutGap(Teacher sg, Chromosome chromosome) {
    int result = universityProperties.getDaysPerWeek();

    List<Integer> slots = teacherTimeSlots(sg, chromosome);
    for (int i = 0; i < slots.size() - 1; i++) {
      Integer curr = slots.get(i);
      Integer next = slots.get(i + 1);
      if (timeSlots.relateToSameDay(curr, next) && isGapBetween(curr, next)) {
        result -= 1;
      }
    }

    return result;
  }

  private List<Integer> teacherTimeSlots(Teacher teacher, Chromosome chromosome) {
    return chromosome.getScheduledClasses()
        .entrySet().stream()
        .map(e -> new Tuple2<>(
            e.getKey(),
            e.getValue().stream()
                .map(SettledClass::getTeachingClass)
                .map(GeneticTeachingClass::involvedTeachers)
                .flatMap(Collection::stream)
                .collect(toSet()))
        ).filter(t -> t._2.contains(teacher))
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

  private int weightForSingleTeacher(int teacherCount) {
    return this.weight() / teacherCount;
  }

  private boolean isGapBetween(Integer curr, Integer next) {
    return next - curr != 1;
  }
}
