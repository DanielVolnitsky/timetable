package com.waytoodanny.timetable.service.generation.genetics.event;

import com.waytoodanny.timetable.service.event.TimetableLifecycleEvent;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.Value;

@Value
public class AlgorithmIteration implements TimetableLifecycleEvent {
  int iteration;
  Population population;
}
