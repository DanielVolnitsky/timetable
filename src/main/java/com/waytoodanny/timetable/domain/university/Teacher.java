package com.waytoodanny.timetable.domain.university;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Teacher {
  private final String name;
  private final String surname;
  private final String secondName;
  private List<String> titles;
}
