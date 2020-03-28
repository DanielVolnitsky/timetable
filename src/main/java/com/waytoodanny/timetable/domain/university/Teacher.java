package com.waytoodanny.timetable.domain.university;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Teacher {
  private final String name;
  private final String surname;
  private final String secondName;
  private List<String> titles;
}
