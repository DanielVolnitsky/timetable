package com.waytoodanny.timetable;

import com.waytoodanny.timetable.domain.university.AvailableRooms;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.Value;

import java.util.Set;

@Value
public class InputData {
  Set<TeachingClass> teachingClasses;
  AvailableRooms rooms;
}
