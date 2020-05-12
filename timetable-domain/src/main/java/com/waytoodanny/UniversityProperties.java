package com.waytoodanny;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

@Data
@Accessors(chain = true)
public class UniversityProperties {
  private int daysPerWeek;
  private int academicWeeks;
  private LocalDate academicYearStartDate;
  private Duration classDuration;
  private List<LocalTime> classStartTimes;
  private int secondShiftStartTimeslot;

  public int timeSlotsPerWeek() {
    return academicHoursPerDay() * daysPerWeek;
  }

  public int academicHoursPerDay() {
    return classStartTimes.size();
  }

  public Set<Integer> weekTimeSlots() {
    return IntStream.rangeClosed(1, timeSlotsPerWeek())
        .boxed()
        .collect(toSet());
  }
}
