package com.waytoodanny.timetable.domain.university;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Rooms {
  private List<Room> rooms;

  public Optional<Room> suitableFor(Predicate<Room> p) {
    return rooms.stream().filter(p).findAny();
  }
}
