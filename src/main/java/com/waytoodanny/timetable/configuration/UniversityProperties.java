package com.waytoodanny.timetable.configuration;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.stream.IntStream;

@Data
@Accessors(chain = true)
@ConfigurationProperties("university")
public class UniversityProperties {
  private int academicHoursPerDay;
  private int daysPerWeek;

  public int timeSlotsPerWeek() {
    return academicHoursPerDay * daysPerWeek;
  }

  public int[] weekPeriods() {
    return IntStream.rangeClosed(1, timeSlotsPerWeek()).toArray();
  }
}
