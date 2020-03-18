package com.waytoodanny.timetable.domain.timetable;

import com.waytoodanny.timetable.domain.university.ClassesStartTimes;
import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InputData {
  private final List<TeachingClass> teachingClasses;
  private final List<Room> rooms;
  private final int workingDaysPerWeek;
  private final ClassesStartTimes classesStartTimes;
}
