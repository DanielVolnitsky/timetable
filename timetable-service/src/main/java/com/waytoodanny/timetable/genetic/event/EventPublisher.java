package com.waytoodanny.timetable.genetic.event;

public interface EventPublisher {
  <T extends TimetableLifecycleEvent> void publish(T event);
}
