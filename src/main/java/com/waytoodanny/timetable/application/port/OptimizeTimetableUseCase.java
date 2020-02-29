package com.waytoodanny.timetable.application.port;

import com.waytoodanny.timetable.domain.timetable.Timetable;

import java.util.function.UnaryOperator;

public interface OptimizeTimetableUseCase extends UnaryOperator<Timetable> {
}
