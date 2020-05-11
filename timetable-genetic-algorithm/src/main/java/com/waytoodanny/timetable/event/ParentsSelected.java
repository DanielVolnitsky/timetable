package com.waytoodanny.timetable.event;

import com.waytoodanny.timetable.domain.Parents;
import lombok.Value;

import java.util.Collection;

@Value
public class ParentsSelected implements TimetableLifecycleEvent {
  Collection<Parents> parents;
}
