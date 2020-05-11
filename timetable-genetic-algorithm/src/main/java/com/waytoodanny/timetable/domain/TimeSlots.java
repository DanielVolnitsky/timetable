package com.waytoodanny.timetable.domain;

import com.waytoodanny.UniversityProperties;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Value
@Component
public class TimeSlots {

  UniversityProperties universityProperties;

  public boolean relateToSameDay(int s1, int s2) {
    int hpd = universityProperties.getAcademicHoursPerDay();
    return Double.compare(
        Math.ceil((double) s1 / hpd),
        Math.ceil((double) s2 / hpd)) == 0;
  }

  public int pairNumber(int timeslotNumber) {
    int hpd = universityProperties.getAcademicHoursPerDay();
    int pairNumber = timeslotNumber % hpd;
    return pairNumber == 0 ? hpd : pairNumber;
  }

  public LocalTime correspondentStartTime(int timeslot) {
    int timeIdx = pairNumber(timeslot) - 1;
    return universityProperties.getClassStartTimes().get(timeIdx);
  }

  public LocalTime correspondentEndTime(int timeslot) {
    return correspondentStartTime(timeslot).plus(universityProperties.getClassDuration());
  }
}
