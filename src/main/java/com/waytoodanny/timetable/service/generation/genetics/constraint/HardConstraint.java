package com.waytoodanny.timetable.service.generation.genetics.constraint;

public interface HardConstraint extends ScheduleConstraint {

  default int weight() {
    return 200;
  }
}
