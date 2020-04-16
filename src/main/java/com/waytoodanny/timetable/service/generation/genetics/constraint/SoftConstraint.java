package com.waytoodanny.timetable.service.generation.genetics.constraint;

public interface SoftConstraint extends ScheduleConstraint {

  default int weight() {
    return 200;
  }
}
