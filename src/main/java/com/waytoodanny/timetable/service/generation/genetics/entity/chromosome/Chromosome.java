package com.waytoodanny.timetable.service.generation.genetics.entity.chromosome;

import com.waytoodanny.timetable.domain.university.AvailableRooms;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import com.waytoodanny.timetable.service.generation.genetics.entity.SettledClass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.*;

import static java.util.stream.Collectors.toList;

@EqualsAndHashCode
@ToString(of = "scheduledClasses")
public class Chromosome {
  @Getter
  private final Map<Integer, List<SettledClass>> scheduledClasses = new TreeMap<>();
  private final Map<Integer, AvailableRooms> timeslotRooms = new HashMap<>();

  public Chromosome(@NonNull AvailableRooms rooms,
                    @NonNull Collection<Integer> timeSlotsPerWeek) {
    timeSlotsPerWeek.stream()
        .sorted()
        .forEach(t -> timeslotRooms.put(t, rooms.copy()));
  }

  public boolean scheduleClass(TeachingClass tClass, int timeslot) {
    AvailableRooms rooms = timeslotRooms.get(timeslot);
    if (rooms.isEmpty()) {
      return false;
    }
    return rooms.withdrawBestSuitableFor(tClass.roomRequirements())
        .map(r -> scheduledClasses.merge(timeslot, List.of(new SettledClass(r, tClass)), this::mergeLists))
        .isPresent();
  }

  public void scheduleClassRandomly(TeachingClass tClass) {
    timeslotRooms.entrySet().stream()
        .filter(e -> e.getValue()
            .getBestSuitableFor(tClass.roomRequirements())
            .isPresent())
        .findAny()
        .map(Map.Entry::getKey)
        .ifPresentOrElse(
            timeslot -> scheduleClass(tClass, timeslot),
            () -> {
              throw new RuntimeException("Failed to schedule class randomly");
            }
        );
  }

  //TODO
  public int timeslotForClass(TeachingClass c) {
    return scheduledClasses.entrySet().stream()
        .filter(e -> e.getValue().stream()
            .map(SettledClass::getTeachingClass)
            .collect(toList())
            .contains(c))
        .map(Map.Entry::getKey)
        .findAny().orElse(0);
  }

  private List<SettledClass> mergeLists(List<SettledClass> l1, List<SettledClass> l2) {
    List<SettledClass> r = new ArrayList<>();
    r.addAll(l1);
    r.addAll(l2);
    return r;
  }
}
