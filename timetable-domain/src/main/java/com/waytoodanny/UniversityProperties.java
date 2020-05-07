package com.waytoodanny;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

@Data
@Accessors(chain = true)
public class UniversityProperties {
  private int academicHoursPerDay;
  private int daysPerWeek;
  private int academicWeeksPerYear;
  private LocalDate academicYearStartDate;

  public int timeSlotsPerWeek() {
    return academicHoursPerDay * daysPerWeek;
  }

  public Set<Integer> weekTimeSlots() {
    return IntStream.rangeClosed(1, timeSlotsPerWeek())
        .boxed()
        .collect(toSet());
  }
}
