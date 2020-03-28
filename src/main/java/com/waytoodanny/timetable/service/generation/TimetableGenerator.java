package com.waytoodanny.timetable.service.generation;

import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.timetable.Timetable;

public interface TimetableGenerator {
  Timetable timetable(InputData input);
}
