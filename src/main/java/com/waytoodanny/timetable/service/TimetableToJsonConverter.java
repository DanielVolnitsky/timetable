package com.waytoodanny.timetable.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waytoodanny.timetable.Timetable;
import com.waytoodanny.timetable.TimetableConverter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TimetableToJsonConverter implements TimetableConverter<String> {

  private final ObjectMapper jsonMapper;

  @Override
  @SneakyThrows
  public String convert(Timetable timetable) {
    return jsonMapper.writeValueAsString(timetable);
  }
}
