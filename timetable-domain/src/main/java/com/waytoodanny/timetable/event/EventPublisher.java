package com.waytoodanny.timetable.event;

public interface EventPublisher {
  <T extends TimetableLifecycleEvent> void publish(T event);
}
