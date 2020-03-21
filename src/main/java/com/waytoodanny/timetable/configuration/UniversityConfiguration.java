package com.waytoodanny.timetable.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.stream.IntStream;

@Data
@ConfigurationProperties("university")
public class UniversityConfiguration {
  private int academicHoursPerDay;
  private int daysPerWeek;
  private int availableRooms;

  public int totalClassesPerWeek() {
    return academicHoursPerDay * daysPerWeek * availableRooms;
  }

  public int timeSlotsPerWeek() {
    return academicHoursPerDay * daysPerWeek;
  }

  public int[] weekPeriods() {
    return IntStream.rangeClosed(1, timeSlotsPerWeek()).toArray();
  }
}
