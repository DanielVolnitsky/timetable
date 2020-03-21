package com.waytoodanny.timetable.domain.university;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

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
    return Collections.nCopies(classesPerWeek, this);
  }

  public Predicate<Room> roomRequirements() {
    return room -> group.getStudentsNumber() <= room.getCapacity();
  }
}
