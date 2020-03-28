package com.waytoodanny.timetable.domain.university;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Rooms {
  private List<Room> rooms;
  @Getter
  private boolean allUnique;

  private Rooms(List<Room> rooms) {
    this.rooms = rooms;
    this.allUnique = new HashSet<>(rooms).size() == rooms.size();
  }

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
