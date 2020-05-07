package com.waytoodanny.timetable.genetic.domain;

import com.waytoodanny.timetable.genetic.domain.university.Room;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class SettledClass {
  Room room;
  GeneticTeachingClass teachingClass;
}
