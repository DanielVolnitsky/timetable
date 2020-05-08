package com.waytoodanny.timetable;

public interface TimetableConverter<T> {
  T convert(Timetable timetable);
}
