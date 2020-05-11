package com.waytoodanny.timetable.genetic.algorithm.impl;

import com.waytoodanny.UniversityProperties;
import com.waytoodanny.timetable.TeachingSlot;
import com.waytoodanny.timetable.Timetable;
import com.waytoodanny.timetable.domain.*;
import com.waytoodanny.timetable.domain.chromosome.Chromosome;
import com.waytoodanny.timetable.domain.chromosome.EvaluatedChromosome;
import com.waytoodanny.timetable.genetic.algorithm.PopulationTimetableConverter;
import io.vavr.collection.Iterator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class PopulationTimetableConverterImpl implements PopulationTimetableConverter {

  private final UniversityProperties universityProperties;
  private final TimeSlots timeSlots;

  @Override
  public Timetable convert(EvaluatedChromosome candidate) {
    if (!candidate.isAcceptable()) {
      throw new IllegalArgumentException(
          "Generated timetable candidate is not acceptable: " + candidate);
    }

    Chromosome chromosome = candidate.getChromosome();

    Map<Integer, List<WeekdayClasses>> byWeek = new TreeMap<>();
    IntStream.rangeClosed(1, universityProperties.getAcademicWeeksPerYear())
        .forEachOrdered(weekNo -> byWeek.put(weekNo, weekdayClasses(chromosome)));

    //TODO :)
    List<TeachingSlot> slots = byWeek.entrySet().stream()
        .map(e -> e.getValue().stream()
            .map(dayClass -> dayClass.getTimeslotClasses().stream()
                .map(timeslotClass -> timeslotClass.getSettledClasses().stream()
                    .map(settledClass -> TeachingSlot.builder()
                        .teachingClass(settledClass.originalClass())
                        .pairNumber(timeSlots.pairNumber(timeslotClass.getTimeslot()))
                        .room(settledClass.getRoom())
                        .timeCoordinates(TimeCoordinates.builder()
                            .weekNumber(e.getKey())
                            .day(dayClass.getDay())
                            .start(timeSlots.correspondentStartTime(timeslotClass.getTimeslot()))
                            .end(timeSlots.correspondentEndTime(timeslotClass.getTimeslot()))
                            .build())
                        .build())
                    .collect(toList()))
                .flatMap(Collection::stream)
                .collect(toList())
            )
            .flatMap(Collection::stream)
            .collect(toList()))
        .flatMap(Collection::stream)
        .collect(toList());

    return new Timetable(slots);
  }

  private List<WeekdayClasses> weekdayClasses(Chromosome chromosome) {
    int slotsPerWeek = universityProperties.timeSlotsPerWeek();
    List<TimeslotClasses> timeslotClasses = chromosome.timeslotClasses();

    Map<Integer, Weekday> weekdaySlots = weekdaySlots(slotsPerWeek);
    Map<Weekday, List<TimeslotClasses>> weekdayClasses = new EnumMap<>(Weekday.class);

    for (TimeslotClasses tc : timeslotClasses) {
      Weekday classDay = weekdaySlots.get(tc.getTimeslot());
      weekdayClasses.merge(
          classDay,
          singletonList(tc),
          (l1, l2) -> {
            var merged = new ArrayList<>(l1);
            merged.addAll(l2);
            return merged;
          });
    }

    return weekdayClasses.entrySet().stream()
        .map(e -> new WeekdayClasses(e.getKey(), e.getValue()))
        .collect(toList());
  }

  private Map<Integer, Weekday> weekdaySlots(int slotsPerWeek) {
    Map<Integer, Weekday> weekdaySlots = new HashMap<>();

    Iterator<io.vavr.collection.Stream<Integer>> grouped =
        io.vavr.collection.Stream.from(1, 1)
            .take(slotsPerWeek)
            .grouped(universityProperties.getAcademicHoursPerDay());

    Arrays.stream(Weekday.values())
        .forEach(wd -> grouped.next().forEach(slot -> weekdaySlots.put(slot, wd)));

    return weekdaySlots;
  }
}
