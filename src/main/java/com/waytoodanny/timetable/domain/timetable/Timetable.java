package com.waytoodanny.timetable.domain.timetable;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
public class Timetable {
  @Singular
  private final List<TeachingSlot> teachingSlots;
}
