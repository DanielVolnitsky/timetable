package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.Rooms;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

import static java.util.Objects.isNull;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Gene {
  @Setter
  @Accessors(fluent = true)
  private int timeSlot;
  @Singular
  private List<Tuple> teachingTuples;

  private Rooms usedRooms;

  public Gene merge(Gene other) {
    GeneBuilder builder = Gene.builder()
        .timeSlot(this.timeSlot)
        .teachingTuples(this.teachingTuples);

    other.getTeachingTuples().forEach(builder::teachingTuple);

    return builder.build();
  }

  public Rooms usedRooms() {
    if (isNull(this.usedRooms)) {
      this.usedRooms = Rooms.of(this.getTeachingTuples().stream()
          .map(Tuple::getRoom)
          .toArray(Room[]::new));
    }
    return this.usedRooms;
  }

  public boolean isAllRoomsUnique() {
    return usedRooms().isAllUnique();
  }

  @Value
  public static class Tuple {
    private final TeachingClass teachingClass;
    private final Room room;

    public static Tuple withAnySuitableRoom(TeachingClass tc, Rooms availableRooms) {
      return new Tuple(tc,
          availableRooms.suitableFor(tc.roomRequirements())
              .orElseThrow(() -> new IllegalArgumentException("No suitable room for " + tc + " found")));
    }
  }
}
