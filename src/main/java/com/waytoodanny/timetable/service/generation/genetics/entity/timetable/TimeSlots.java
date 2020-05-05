package com.waytoodanny.timetable.service.generation.genetics.entity.timetable;

import com.waytoodanny.timetable.configuration.properties.UniversityProperties;
import lombok.Value;
import org.springframework.stereotype.Component;

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
}
