package com.waytoodanny.timetable.service.generation.genetics.constraint.soft;

import com.waytoodanny.timetable.configuration.UniversityProperties;
import com.waytoodanny.timetable.domain.timetable.TimeSlots;
import com.waytoodanny.timetable.domain.university.StudentGroup;
import com.waytoodanny.timetable.service.generation.genetics.constraint.SoftConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.Gene;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
@Component
public class NoGapsForStudentGroup implements SoftConstraint {

  UniversityProperties universityProperties;
  TimeSlots timeSlots;

  @Override
  public FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial) {
    List<StudentGroup> allGroups = allStudentGroups(chromosome);
    int weightForSingleGroup = weightForSingleGroup(allGroups.size());

    return allGroups.stream()
        .map(group -> daysWithoutGap(group, chromosome))
        .map(daysWithoutGap -> fitnessValueForNoGapDays(weightForSingleGroup, daysWithoutGap))
        .reduce(Integer::sum)
        .map(initial::plus)
        .orElse(initial);
  }

  private int fitnessValueForNoGapDays(int weightForSingleGroup, Integer daysWithoutGap) {
    return daysWithoutGap * weightForSingleDay(weightForSingleGroup, universityProperties.getDaysPerWeek());
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

  private boolean isGapBetween(Integer curr, Integer next) {
    return next - curr != 1;
  }

  private List<Integer> studentGroupTimeSlots(StudentGroup sg, Chromosome chromosome) {
    return chromosome.getGenes().stream()
        .filter(g -> g.studentGroupsInvolved().contains(sg))
        .map(Gene::timeSlot)
        .sorted()
        .collect(toList());
  }

  private List<StudentGroup> allStudentGroups(Chromosome chromosome) {
    return chromosome.getGenes().stream()
        .map(Gene::studentGroupsInvolved)
        .flatMap(Collection::stream)
        .distinct()
        .collect(toList());
  }

  private int weightForSingleDay(int weightForSingleGroup, int daysCount) {
    return weightForSingleGroup / daysCount;
  }

  private int weightForSingleGroup(int groupsCount) {
    return this.weight() / groupsCount;
  }
}
