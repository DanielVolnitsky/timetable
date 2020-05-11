package com.waytoodanny.timetable.domain;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.StudentGroup;
import com.waytoodanny.timetable.domain.university.Teacher;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.ToString;
import lombok.Value;

import java.util.Set;
import java.util.function.Predicate;

@Value
@ToString
public class GeneticTeachingClass {

  TeachingClass teachingClass;

  public Predicate<Room> roomRequirements() {
    return room -> studentsOverall() <= room.getCapacity();
  }

  public Set<Teacher> involvedTeachers() {
    return teachingClass.getTeachers();
  }

  public Set<StudentGroup> involvedStudentGroups() {
    return teachingClass.getGroups();
  }

  public boolean hasCommonTeachers(GeneticTeachingClass other) {
    var otherTeachers = other.involvedTeachers();
    return this.involvedTeachers().stream().anyMatch(otherTeachers::contains);
  }

  public boolean hasCommonStudentGroups(GeneticTeachingClass other) {
    var otherGroups = other.involvedStudentGroups();
    return this.involvedStudentGroups().stream().anyMatch(otherGroups::contains);
  }

  //TODO cache
  private Integer studentsOverall() {
    return teachingClass.getGroups().stream()
        .map(StudentGroup::getStudentsNumber)
        .reduce(Integer::sum)
        .orElse(0);
  }
}
