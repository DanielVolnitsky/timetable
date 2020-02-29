package com.waytoodanny.timetable.application.service;

import com.waytoodanny.timetable.application.port.GenerateTimetableUseCase;
import com.waytoodanny.timetable.application.port.GetTimetableUseCase;
import com.waytoodanny.timetable.application.port.OptimizeTimetableUseCase;
import com.waytoodanny.timetable.domain.timetable.Timetable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetTimetableService implements GetTimetableUseCase {

  private final GenerateTimetableUseCase timetableSupplier;
  private final OptimizeTimetableUseCase timetableOptimizer;

  @Override
  public Timetable get() {
    return timetableOptimizer.apply(timetableSupplier.get());
  }
}
