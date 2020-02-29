package com.waytoodanny.timetable.application.port;

import com.waytoodanny.timetable.domain.timetable.Timetable;

import java.util.function.Supplier;

public interface GenerateTimetableUseCase extends Supplier<Timetable> {
}
