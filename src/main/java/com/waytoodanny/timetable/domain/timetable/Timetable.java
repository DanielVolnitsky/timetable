package com.waytoodanny.timetable.domain.timetable;

import lombok.Builder;

import java.util.List;

@Builder
public class Timetable {
  private final List<TeachingSlot> teachingSlots;
}
