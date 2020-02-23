package com.waytoodanny.timetable.domain;

import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder
public class Timetable {

  @Singular
  private Set<Period> periods;
}
