package com.waytoodanny.timetable;

import com.waytoodanny.domain.university.AvailableRooms;
import com.waytoodanny.domain.university.TeachingClass;
import lombok.Value;

import java.util.Set;

@Value
public class InputData {
  Set<TeachingClass> teachingClasses;
  AvailableRooms rooms;
}
