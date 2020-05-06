package com.waytoodanny.event;

import com.waytoodanny.entity.Population;
import lombok.Value;

@Value
public class AlgorithmIteration implements TimetableLifecycleEvent {
  int iteration;
  Population population;
}
