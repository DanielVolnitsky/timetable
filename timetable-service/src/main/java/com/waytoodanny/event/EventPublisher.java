package com.waytoodanny.event;

public interface EventPublisher {
  <T extends TimetableLifecycleEvent> void publish(T event);
}
