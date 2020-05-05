package com.waytoodanny.timetable.domain.university.teachingclass;

import com.waytoodanny.timetable.domain.university.ClassType;
import com.waytoodanny.timetable.domain.university.StudentGroup;
import com.waytoodanny.timetable.domain.university.Subject;
import com.waytoodanny.timetable.domain.university.Teacher;
import lombok.Value;

import java.util.Set;

@Value
public class CompositeTeachingClass implements TeachingClass {
  Subject subject;
  ClassType type;
  Set<Teacher> teachers;
  Set<StudentGroup> groups;
}
