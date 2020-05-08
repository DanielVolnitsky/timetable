package com.waytoodanny.timetable.genetic.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
@EqualsAndHashCode
public class TimeCoordinates {
  int weekNumber;
  Weekday day;
  LocalTime start;
  LocalTime end;
}
