package com.waytoodanny.timetable.service.generation.genetics.entity.chromosome;

import com.waytoodanny.timetable.domain.university.AvailableRooms;
import com.waytoodanny.timetable.domain.university.TeachingClass;
import com.waytoodanny.timetable.service.generation.genetics.entity.SettledClass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;

@EqualsAndHashCode
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

  private List<SettledClass> mergeLists(List<SettledClass> l1, List<SettledClass> l2) {
    List<SettledClass> r = new ArrayList<>();
    r.addAll(l1);
    r.addAll(l2);
    return r;
  }
}
