package com.waytoodanny.timetable.domain;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class SettledClass {
  Room room;
  GeneticTeachingClass geneticTeachingClass;

  public TeachingClass originalClass() {
    return geneticTeachingClass.getTeachingClass();
  }
}
