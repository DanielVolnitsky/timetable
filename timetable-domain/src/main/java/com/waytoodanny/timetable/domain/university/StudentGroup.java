package com.waytoodanny.timetable.domain.university;

import lombok.ToString;
import lombok.Value;

@Value
@ToString(of = "name")
public class StudentGroup {
  String name;
  int studentsNumber;
  int shift;
}
