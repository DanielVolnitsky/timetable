package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(of = {"teachingClass", "room"})
public class SettledClass {
  Room room;
  TeachingClass teachingClass;
}
