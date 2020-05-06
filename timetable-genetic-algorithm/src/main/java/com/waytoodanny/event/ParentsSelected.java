package com.waytoodanny.event;

import com.waytoodanny.entity.timetable.Parents;
import lombok.Value;

import java.util.Collection;

@Value
public class ParentsSelected implements TimetableLifecycleEvent {
  Collection<Parents> parents;
}
