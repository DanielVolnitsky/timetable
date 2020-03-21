package com.waytoodanny.timetable.domain.timetable;

import com.waytoodanny.timetable.domain.university.Rooms;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@EqualsAndHashCode
public class InputData {
  @Getter
  private final Set<TeachingClass> teachingClasses;
  @Getter
  private final Rooms rooms;

  private List<TeachingClass> singleWeekClasses;

  public List<TeachingClass> singleWeekClasses() {
    if (nonNull(singleWeekClasses)) {
      return singleWeekClasses;
    }
    singleWeekClasses = teachingClasses.stream()
        .flatMap(c -> c.singleWeekClasses().stream())
        .collect(toList());
    return singleWeekClasses;
  }
}
