package com.waytoodanny.timetable.genetic.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
public class TimeCoordinates {
  WeekCoordinates weekCoordinates;
  Weekday day;
  LocalDateTime start;
  LocalDateTime end;
}
