package com.waytoodanny.timetable.domain.university;

import com.waytoodanny.util.Prototyped;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Set;

@Builder
@Getter
public class TeachingClass implements Prototyped<TeachingClass> {

  private final Subject subject;
  @Singular
  private final Set<Teacher> teachers;
  @Singular
  private final Set<StudentGroup> groups;
  private final ClassType type;

  private final int classesNumber;
  private final int classesPerWeek;

  @Override
  public TeachingClass prototype() {
    return new TeachingClass(
        subject, teachers, groups, type, classesNumber, classesPerWeek);
  }
}
