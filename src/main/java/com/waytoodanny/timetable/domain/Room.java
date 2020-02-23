package com.waytoodanny.timetable.domain;

import lombok.Builder;

@Builder
public class Room {
  private final String name;
  private final int capacity;
}
