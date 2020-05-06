package com.waytoodanny.entity;

import com.waytoodanny.domain.university.Room;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(of = {"teachingClass", "room"})
public class SettledClass {
  Room room;
  GeneticTeachingClass teachingClass;
}
