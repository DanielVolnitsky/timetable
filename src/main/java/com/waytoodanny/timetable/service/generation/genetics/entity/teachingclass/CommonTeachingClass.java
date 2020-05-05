package com.waytoodanny.timetable.service.generation.genetics.entity.teachingclass;

import com.waytoodanny.timetable.domain.university.ClassType;
import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.StudentGroup;
import com.waytoodanny.timetable.domain.university.Subject;
import com.waytoodanny.timetable.domain.university.Teacher;
import lombok.Builder;
import lombok.ToString;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@ToString(of = {"teacher", "group", "subject", "type"})
public class CommonTeachingClass implements TeachingClass {
  private final Subject subject;
  private final Teacher teacher;
  private final StudentGroup group;
  private final ClassType type;

  private final int classesNumber;
  private final int classesPerWeek;

  private Set<Teacher> involvedTeachers;
  private Set<StudentGroup> involvedGroups;

  @Builder
  private CommonTeachingClass(Subject subject,
                              Teacher teacher,
                              StudentGroup group,
                              ClassType type,
                              int classesNumber,
                              int classesPerWeek) {
    this.subject = subject;
    this.teacher = teacher;
    this.group = group;
    this.type = type;
    this.classesNumber = classesNumber;
    this.classesPerWeek = classesPerWeek;
  }

  public List<CommonTeachingClass> classCopiesForWeeklyScheduling() {
    return IntStream.range(0, classesPerWeek).mapToObj(i -> copy()).collect(toList());
  }

  public Predicate<Room> roomRequirements() {
    return room -> group.getStudentsNumber() <= room.getCapacity();
  }

  @Override
  public Set<Teacher> involvedTeachers() {
    if (nonNull(involvedTeachers)) {
      return involvedTeachers;
    }
    involvedTeachers = Set.of(teacher);
    return involvedTeachers;
  }

  @Override
  public Set<StudentGroup> involvedStudentGroups() {
    if (nonNull(involvedGroups)) {
      return involvedGroups;
    }
    involvedGroups = Set.of(group);
    return involvedGroups;
  }

  @Override
  public boolean hasCommonTeachers(TeachingClass other) {
    return other.involvedTeachers().contains(teacher);
  }

  @Override
  public boolean hasCommonStudentGroups(TeachingClass other) {
    return other.involvedStudentGroups().contains(group);
  }

  private CommonTeachingClass copy() {
    return new CommonTeachingClass(subject, teacher, group, type, classesNumber, classesPerWeek);
  }
}
