package com.waytoodanny.timetable.domain;

import com.waytoodanny.UniversityProperties;
import lombok.Value;
import org.springframework.stereotype.Component;

@Value
@Component
public class Shifts {
  UniversityProperties properties;
  TimeSlots timeSlots;

  public int timeslotShift(int timeslot) {
    return properties.getSecondShiftStartTimeslot() > timeSlots.pairNumber(timeslot) ? 1 : 2;
  }
}
