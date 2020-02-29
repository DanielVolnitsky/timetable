package com.waytoodanny.timetable.domain.timetable;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class TeachingSlot {
  private final TeachingClass teachingClass;
  private final Room room;
  private final LocalDateTime start;
  private final LocalDateTime end;
}
