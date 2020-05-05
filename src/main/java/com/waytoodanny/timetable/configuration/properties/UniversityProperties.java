package com.waytoodanny.timetable.configuration.properties;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

@Data
@Accessors(chain = true)
@ConfigurationProperties("university")
public class UniversityProperties {
  private int academicHoursPerDay;
  private int daysPerWeek;

  public int timeSlotsPerWeek() {
    return academicHoursPerDay * daysPerWeek;
  }

  public Set<Integer> weekTimeSlots() {
    return IntStream.rangeClosed(1, timeSlotsPerWeek())
        .boxed()
        .collect(toSet());
  }
}
