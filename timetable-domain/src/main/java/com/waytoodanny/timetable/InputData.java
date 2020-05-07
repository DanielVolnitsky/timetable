package com.waytoodanny.timetable;

import com.waytoodanny.timetable.genetic.domain.university.AvailableRooms;
import com.waytoodanny.timetable.genetic.domain.university.TeachingClass;
import lombok.Value;

import java.util.Set;

@Value
public class InputData {
  Set<TeachingClass> teachingClasses;
  AvailableRooms rooms;
}
