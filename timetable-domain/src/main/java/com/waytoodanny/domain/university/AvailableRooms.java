package com.waytoodanny.domain.university;

import com.waytoodanny.util.Merging;
import com.waytoodanny.util.Prototyped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AvailableRooms
    implements Prototyped<AvailableRooms>, Merging<AvailableRooms> {

  private final List<Room> rooms;

  public static AvailableRooms of(Room... rooms) {
    return new AvailableRooms(Arrays.asList(rooms));
  }

  public Optional<Room> findBestSuitableFor(Predicate<Room> requirements) {
    return rooms.stream()
        .filter(requirements)
        .min(Comparator.comparingInt(Room::getCapacity));
  }

  public Optional<Room> withdrawBestSuitableFor(Predicate<Room> requirements) {
    return rooms.stream()
        .filter(requirements)
        .min(Comparator.comparingInt(Room::getCapacity))
        .map(r -> {
          rooms.remove(r);
          return r;
        });
  }

  @Override
  public AvailableRooms prototype() {
    return new AvailableRooms(new ArrayList<>(this.rooms));
  }

  @Override
  public AvailableRooms merge(AvailableRooms other) {
    this.rooms.addAll(other.rooms);
    return this;
  }

  public int size() {
    return rooms.size();
  }

  public boolean isEmpty() {
    return rooms.isEmpty();
  }
}
