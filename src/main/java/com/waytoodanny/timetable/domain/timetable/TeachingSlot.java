package com.waytoodanny.timetable.domain.timetable;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.teachingclass.CommonTeachingClass;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TeachingSlot {
  private final CommonTeachingClass teachingClass;
  private final Room room;
  private final LocalDateTime start;
  private final LocalDateTime end;
}
