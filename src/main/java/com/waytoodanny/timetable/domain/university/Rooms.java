package com.waytoodanny.timetable.domain.university;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Rooms {
  private List<Room> rooms;

  public static Rooms of(Room... rooms) {
    return new Rooms(Arrays.asList(rooms));
  }

  public int size() {
    return rooms.size();
  }

  public Optional<Room> suitableFor(Predicate<Room> p) {
    return rooms.stream().filter(p).findAny();
  }
}
