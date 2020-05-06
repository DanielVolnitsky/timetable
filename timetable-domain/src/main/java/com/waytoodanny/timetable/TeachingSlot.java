package com.waytoodanny.timetable;

import com.waytoodanny.domain.university.Room;
import com.waytoodanny.domain.university.TeachingClass;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TeachingSlot {
  private final TeachingClass teachingClass;
  private final Room room;
  private final LocalDateTime start;
  private final LocalDateTime end;
}
