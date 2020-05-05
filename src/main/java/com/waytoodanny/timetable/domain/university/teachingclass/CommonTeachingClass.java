package com.waytoodanny.timetable.domain.university.teachingclass;

import com.waytoodanny.timetable.domain.university.ClassType;
import com.waytoodanny.timetable.domain.university.StudentGroup;
import com.waytoodanny.timetable.domain.university.Subject;
import com.waytoodanny.timetable.domain.university.Teacher;
import lombok.Value;

@Value
public class CommonTeachingClass implements TeachingClass {
  Subject subject;
  ClassType type;
  Teacher teacher;
  StudentGroup group;
}
