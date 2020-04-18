package com.waytoodanny.timetable.domain.university;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Getter
@Builder
@ToString
public class TeachingClass {
  private final Subject subject;
  private final Teacher teacher;
  private final StudentGroup group;
  private final int classesNumber;
  private final int classesPerWeek;

  public List<TeachingClass> singleWeekClasses() {
    return IntStream.range(0, classesPerWeek).mapToObj(i -> copy()).collect(toList());
  }

  private TeachingClass copy() {
    return new TeachingClass(subject, teacher, group, classesNumber, classesPerWeek);
  }

  public Predicate<Room> roomRequirements() {
    return room -> group.getStudentsNumber() <= room.getCapacity();
  }
}
