package com.waytoodanny.timetable.genetic.domain;

import lombok.Value;

import java.util.List;

@Value
public class TimeslotClasses {
  Integer timeslot;
  List<SettledClass> settledClasses;
}
