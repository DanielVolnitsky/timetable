package com.waytoodanny.timetable.service.event;

public interface EventPublisher {
  <T extends TimetableLifecycleEvent> void publish(T event);
}
