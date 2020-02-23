package com.waytoodanny.timetable.domain;

import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public class Teacher {
  private String name;
  private String surname;
  private String secondName;

  @Singular
  private List<Subject> subjects;
}
