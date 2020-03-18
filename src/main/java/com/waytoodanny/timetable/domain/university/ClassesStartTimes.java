package com.waytoodanny.timetable.domain.university;

import lombok.Builder;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents collection of classes starting hours
 */
@Builder
public class ClassesStartTimes {
  @Singular
  private List<LocalDateTime> values;
}
