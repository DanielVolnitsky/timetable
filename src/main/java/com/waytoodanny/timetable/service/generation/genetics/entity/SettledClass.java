package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.Value;

@Value
public class SettledClass {
  Room room;
  TeachingClass teachingClass;
}
