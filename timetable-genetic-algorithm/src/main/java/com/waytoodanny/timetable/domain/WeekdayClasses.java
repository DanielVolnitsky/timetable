package com.waytoodanny.timetable.domain;

import lombok.Value;

import java.util.List;

@Value
public class WeekdayClasses {
  Weekday day;
  List<TimeslotClasses> timeslotClasses;
}
