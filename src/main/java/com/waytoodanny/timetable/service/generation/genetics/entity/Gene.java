package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.domain.university.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

@Builder
@EqualsAndHashCode
@ToString
public class Gene {

  @Getter
  @Setter
  @Accessors(fluent = true)
  private int timeSlot;

  @Getter
  @Singular
  private List<Tuple> teachingTuples;

  private Rooms usedRooms;
  private List<Teacher> teachersInvolved;
  private List<StudentGroup> studentGroupsInvolved;

  public Gene merge(Gene other) {
    GeneBuilder builder = Gene.builder()
        .timeSlot(this.timeSlot)
        .teachingTuples(this.teachingTuples);

    other.teachingTuples.forEach(builder::teachingTuple);

    return builder.build();
  }

  public Rooms usedRooms() {
    if (isNull(this.usedRooms)) {
      this.usedRooms = Rooms.of(this.teachingTuples.stream()
          .map(Tuple::getRoom)
          .toArray(Room[]::new));
    }
    return this.usedRooms;
  }

  public boolean isAllRoomsUnique() {
    return usedRooms().isAllUnique();
  }

  public List<Teacher> teachersInvolved() {
    if (isEmpty(teachersInvolved)) {
      this.teachersInvolved = this.teachingTuples.stream()
          .map(Tuple::getTeachingClass)
          .map(TeachingClass::getTeacher)
          .collect(toList());
    }
    return this.teachersInvolved;
  }

  public boolean isEachTeacherOccursOnce() {
    var ti = teachersInvolved();
    return ti.size() == new HashSet<>(ti).size();
  }

  public List<StudentGroup> studentGroupsInvolved() {
    if (isEmpty(studentGroupsInvolved)) {
      this.studentGroupsInvolved = this.teachingTuples.stream()
          .map(Tuple::getTeachingClass)
          .map(TeachingClass::getGroup)
          .collect(toList());
    }
    return this.studentGroupsInvolved;
  }

  public boolean isEachStudentGroupOccursOnce() {
    var sg = studentGroupsInvolved();
    return sg.size() == new HashSet<>(sg).size();
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
