package com.waytoodanny.timetable.domain.university;

import lombok.Builder;

@Builder
public class Teacher {
  private final String name;
  private final String surname;
  private final String secondName;
  private String titles;
}
