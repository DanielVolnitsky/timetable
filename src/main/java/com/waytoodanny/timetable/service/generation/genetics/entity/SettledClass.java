package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.Rooms;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.Value;

@Value
public class SettledClass {
  TeachingClass teachingClass;
  Room room;

  public static SettledClass withAnySuitableRoom(TeachingClass tc, Rooms availableRooms) {
    return new SettledClass(tc,
        availableRooms.suitableFor(tc.roomRequirements())
            .orElseThrow(() -> new IllegalArgumentException("No suitable room for " + tc + " found")));
  }
}
