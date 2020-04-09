package com.waytoodanny.timetable.service.generation.genetics.entity;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.Rooms;
import com.waytoodanny.timetable.domain.university.StudentGroup;
import com.waytoodanny.timetable.domain.university.Teacher;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
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
  private List<SettledClass> settledClasses;

  private Rooms usedRooms;
  private List<Teacher> teachersInvolved;
  private List<StudentGroup> studentGroupsInvolved;

  public Gene merge(Gene other) {
    GeneBuilder builder = Gene.builder()
        .timeSlot(this.timeSlot)
        .settledClasses(this.settledClasses);

    other.settledClasses.forEach(builder::settledClass);

    return builder.build();
  }

  public Rooms usedRooms() {
    if (isNull(this.usedRooms)) {
      this.usedRooms = Rooms.of(this.settledClasses.stream()
          .map(SettledClass::getRoom)
          .toArray(Room[]::new));
    }
    return this.usedRooms;
  }

  public boolean isAllRoomsUnique() {
    return usedRooms().isAllUnique();
  }

  public List<Teacher> teachersInvolved() {
    if (isEmpty(teachersInvolved)) {
      this.teachersInvolved = this.settledClasses.stream()
          .map(SettledClass::getTeachingClass)
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
      this.studentGroupsInvolved = this.settledClasses.stream()
          .map(SettledClass::getTeachingClass)
          .map(TeachingClass::getGroup)
          .collect(toList());
    }
    return this.studentGroupsInvolved;
  }

  public boolean isEachStudentGroupOccursOnce() {
    var sg = studentGroupsInvolved();
    return sg.size() == new HashSet<>(sg).size();
  }
}
