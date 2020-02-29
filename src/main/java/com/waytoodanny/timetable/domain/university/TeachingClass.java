package com.waytoodanny.timetable.domain.university;

import lombok.Builder;

@Builder
public class TeachingClass {
  private final Subject subject;
  private final Teacher teacher;
  private final StudentGroup group;
  private final int classesNumber;
}
