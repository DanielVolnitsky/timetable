package com.waytoodanny.timetable.domain;

import lombok.Builder;

import java.util.List;

@Builder
public class StudentGroup {
  private String name;
  private int studentNumber;
  private List<Subject> subjects;
}
