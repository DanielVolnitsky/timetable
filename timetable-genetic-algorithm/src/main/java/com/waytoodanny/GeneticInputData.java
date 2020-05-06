package com.waytoodanny;

import com.waytoodanny.domain.university.TeachingClass;
import com.waytoodanny.entity.GeneticTeachingClass;
import com.waytoodanny.timetable.InputData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class GeneticInputData {

  @Getter
  private final InputData inputData;

  private List<GeneticTeachingClass> singleWeekClasses;

  //TODO cache
  public List<GeneticTeachingClass> classesToScheduleForWeek() {
    if (nonNull(singleWeekClasses)) {
      return singleWeekClasses;
    }

    singleWeekClasses = inputData.getTeachingClasses().stream()
        .flatMap(this::classCopiesForWeeklyScheduling)
        .map(GeneticTeachingClass::new)
        .collect(toList());

    return singleWeekClasses;
  }

  private Stream<TeachingClass> classCopiesForWeeklyScheduling(TeachingClass tc) {
    return IntStream.range(0, tc.getClassesPerWeek())
        .mapToObj(i -> tc.prototype());
  }
}
