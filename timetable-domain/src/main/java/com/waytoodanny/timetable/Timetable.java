package com.waytoodanny.timetable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class Timetable {
  @Singular
  private final List<TeachingSlot> teachingSlots;
}
