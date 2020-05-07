package com.waytoodanny.timetable.genetic.constraint;

public interface HardConstraint extends ScheduleConstraint {

  default int weight() {
    return 200;
  }
}
