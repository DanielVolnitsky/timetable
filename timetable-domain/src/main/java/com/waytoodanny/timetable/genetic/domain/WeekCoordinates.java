package com.waytoodanny.timetable.genetic.domain;

import lombok.Value;

import java.time.LocalDate;

@Value
public class WeekCoordinates {
  int weekNumber;
  LocalDate from;
  LocalDate to;

  public static WeekCoordinates ofNumber(int weekNumber, LocalDate firstMonday) {
    //TODO
    return null;
  }
}
