package com.waytoodanny.timetable.event;

import com.waytoodanny.timetable.genetic.domain.Population;
import lombok.Value;

@Value
public class AlgorithmIteration implements TimetableLifecycleEvent {
  int iteration;
  Population population;
}
