package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.Rooms;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

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

  public Gene merge(Gene other) {
    GeneBuilder builder = Gene.builder()
        .timeSlot(this.timeSlot)
        .teachingTuples(this.teachingTuples);

    other.getTeachingTuples().forEach(builder::teachingTuple);

    return builder.build();
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
