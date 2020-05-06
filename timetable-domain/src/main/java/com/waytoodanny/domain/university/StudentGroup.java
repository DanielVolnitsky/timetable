package com.waytoodanny.domain.university;

import lombok.ToString;
import lombok.Value;

@Value
@ToString(of = "name")
public class StudentGroup {
  String name;
  int studentsNumber;
}
