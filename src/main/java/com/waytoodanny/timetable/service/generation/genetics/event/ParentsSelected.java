package com.waytoodanny.timetable.service.generation.genetics.event;

import com.waytoodanny.timetable.service.event.TimetableLifecycleEvent;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import lombok.Value;

import java.util.Collection;

@Value
public class ParentsSelected implements TimetableLifecycleEvent {
  Collection<Parents> parents;
}
