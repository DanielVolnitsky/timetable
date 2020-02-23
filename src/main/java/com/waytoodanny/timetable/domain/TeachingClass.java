package com.waytoodanny.timetable.domain;

import lombok.Builder;

@Builder
public class TeachingClass {
  private final Teacher teacher;
  private final Subject subject;
  private final Room room;
  private final StudentGroup group;
}
