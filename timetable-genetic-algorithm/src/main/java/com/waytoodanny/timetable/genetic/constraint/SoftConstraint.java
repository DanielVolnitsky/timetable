package com.waytoodanny.timetable.genetic.constraint;

public interface SoftConstraint extends ScheduleConstraint {

  default int weight() {
    return 200;
  }
}
