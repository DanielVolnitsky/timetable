package com.waytoodanny.timetable.domain.university;

import java.util.*;
import java.util.function.Predicate;

public class AvailableRooms {
  private final List<Room> rooms;

  private AvailableRooms(List<Room> rooms) {
    this.rooms = rooms;
  }

  public static AvailableRooms of(Room... rooms) {
    return new AvailableRooms(Arrays.asList(rooms));
  }

  public AvailableRooms copy() {
    return new AvailableRooms(new ArrayList<>(this.rooms));
  }

  public int size() {
    return rooms.size();
  }

  public boolean isEmpty() {
    return rooms.isEmpty();
  }

  public Optional<Room> suitableFor(Predicate<Room> p) {
    return rooms.stream().filter(p).findAny();
  }

  public Optional<Room> withdrawBestSuitableFor(Predicate<Room> p) {
    return rooms.stream()
        .filter(p)
        .min(Comparator.comparingInt(Room::getCapacity))
        .map(r -> {
          rooms.remove(r);
          return r;
        });
  }
}
