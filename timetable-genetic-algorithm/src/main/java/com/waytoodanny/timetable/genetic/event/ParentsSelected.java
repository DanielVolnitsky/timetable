package com.waytoodanny.timetable.genetic.event;

import com.waytoodanny.timetable.genetic.domain.Parents;
import lombok.Value;

import java.util.Collection;

@Value
public class ParentsSelected implements TimetableLifecycleEvent {
  Collection<Parents> parents;
}
