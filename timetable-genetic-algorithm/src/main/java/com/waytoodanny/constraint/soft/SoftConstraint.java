package com.waytoodanny.constraint.soft;

import com.waytoodanny.constraint.ScheduleConstraint;

public interface SoftConstraint extends ScheduleConstraint {

  default int weight() {
    return 200;
  }
}
