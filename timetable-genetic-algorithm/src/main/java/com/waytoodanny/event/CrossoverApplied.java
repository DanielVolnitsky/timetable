package com.waytoodanny.event;

import com.waytoodanny.entity.Population;
import lombok.Value;

@Value
public class CrossoverApplied implements TimetableLifecycleEvent {
  Population population;
}
