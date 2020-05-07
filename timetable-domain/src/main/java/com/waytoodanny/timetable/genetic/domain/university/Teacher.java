package com.waytoodanny.timetable.genetic.domain.university;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Teacher {
  private final String name;
}
