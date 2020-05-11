package com.waytoodanny.timetable.event;

import com.waytoodanny.timetable.domain.Population;
import lombok.Value;

@Value
public class AlgorithmIteration implements TimetableLifecycleEvent {
  int iteration;
  Population population;
}
