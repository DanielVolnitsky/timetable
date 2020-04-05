package com.waytoodanny.timetable.configuration;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Accessors(chain = true)
@ConfigurationProperties("university")
public class UniversityProperties {
  private int academicHoursPerDay;
  private int daysPerWeek;

  public int timeSlotsPerWeek() {
    return academicHoursPerDay * daysPerWeek;
  }
}
