package com.waytoodanny.constraint;

public interface HardConstraint extends ScheduleConstraint {

  default int weight() {
    return 200;
  }
}
