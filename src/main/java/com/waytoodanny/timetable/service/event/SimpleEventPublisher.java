package com.waytoodanny.timetable.service.event;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SimpleEventPublisher implements EventPublisher {

  private final ApplicationEventPublisher eventPublisher;

  @Override
  public <T extends TimetableLifecycleEvent> void publish(T event) {
    eventPublisher.publishEvent(event);
  }
}
