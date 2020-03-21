package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Builder
public class Gene {
  private int timeSlot;
  @Singular
  private List<Tuple> teachingTuples;

  @Value
  public static class Tuple {
    private final TeachingClass teachingClass;
    private final Room room;
  }
}
