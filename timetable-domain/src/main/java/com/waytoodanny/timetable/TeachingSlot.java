package com.waytoodanny.timetable;

import com.waytoodanny.timetable.domain.TimeCoordinates;
import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class TeachingSlot {
  private final TeachingClass teachingClass;
  private final Room room;
  private final int pairNumber;
  private final TimeCoordinates timeCoordinates;
}


