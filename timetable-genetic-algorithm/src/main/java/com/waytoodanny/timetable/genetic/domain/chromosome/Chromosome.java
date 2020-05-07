package com.waytoodanny.timetable.genetic.domain.chromosome;

import com.waytoodanny.timetable.genetic.domain.GeneticTeachingClass;
import com.waytoodanny.timetable.genetic.domain.SettledClass;
import com.waytoodanny.timetable.genetic.domain.TimeslotClasses;
import com.waytoodanny.timetable.genetic.domain.university.AvailableRooms;
import com.waytoodanny.util.Prototyped;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.toList;

@EqualsAndHashCode
@ToString(of = "scheduledClasses")
public class Chromosome implements Prototyped<Chromosome> {
  @Getter
  private final Map<Integer, List<SettledClass>> scheduledClasses = new TreeMap<>();
  private final Map<Integer, AvailableRooms> timeslotRooms = new HashMap<>();

  public Chromosome(@NonNull AvailableRooms rooms,
                    @NonNull Collection<Integer> timeSlotsPerWeek) {
    timeSlotsPerWeek.stream()
        .sorted()
        .forEach(t -> timeslotRooms.put(t, rooms.prototype()));
  }

  private Chromosome(Map<Integer, List<SettledClass>> scheduledClasses,
                     Map<Integer, AvailableRooms> timeslotRooms) {
    scheduledClasses.entrySet()
        .forEach(e -> this.scheduledClasses.put(e.getKey(), new ArrayList<>(e.getValue())));
    this.timeslotRooms.putAll(timeslotRooms);
  }

  public boolean scheduleClass(GeneticTeachingClass tClass, int timeslot) {
//    if(!canScheduleClass(tClass, timeslot)){
//      return false;
//    }

    AvailableRooms rooms = timeslotRooms.get(timeslot);
    if (rooms.isEmpty()) {
      return false;
    }

    return rooms.withdrawBestSuitableFor(tClass.roomRequirements())
        .map(r ->
            scheduledClasses.merge(
                timeslot,
                new ArrayList<>(List.of(new SettledClass(r, tClass))),
                this::mergeLists))
        .isPresent();
  }

  public List<TimeslotClasses> timeslotClasses() {
    return scheduledClasses.entrySet().stream()
        .map(e -> new TimeslotClasses(e.getKey(), e.getValue()))
        .collect(toList());
  }

  private boolean canScheduleClass(GeneticTeachingClass candidateClass, int timeslot) {
    List<SettledClass> timeslotClasses = scheduledClasses.get(timeslot);
    if (timeslotClasses == null) {
      return true;
    }
    return timeslotClasses.stream()
        .map(SettledClass::getGeneticTeachingClass)
        .noneMatch(scheduledClass ->
            scheduledClass.hasCommonStudentGroups(candidateClass)
                || scheduledClass.hasCommonTeachers(candidateClass));
  }

  public boolean scheduleClassRandomly(GeneticTeachingClass tClass) {
    return timeslotRooms.entrySet()
        .stream()
        //TODO
        .parallel()
        .filter(e -> e.getValue()
            .findBestSuitableFor(tClass.roomRequirements())
            .isPresent())
        .filter(e -> canScheduleClass(tClass, e.getKey()))
        .findAny()
        .map(Map.Entry::getKey)
        .map(timeslot -> scheduleClass(tClass, timeslot))
        .orElse(false);
  }

  //TODO decide where parallelization is needed or consequent class scheduling is appropriate decision
  public int timeslotForClass(GeneticTeachingClass teachingClass) {
    return scheduledClasses.entrySet().stream()
        .filter(e -> e.getValue().stream()
            .map(SettledClass::getGeneticTeachingClass)
            .collect(toList())
            .contains(teachingClass))
        .map(Map.Entry::getKey)
        .findAny()
        .orElseThrow();
  }

  private List<SettledClass> mergeLists(List<SettledClass> l1, List<SettledClass> l2) {
    List<SettledClass> r = new ArrayList<>();
    r.addAll(l1);
    r.addAll(l2);
    return r;
  }

  //TODO
  public boolean reschedule(GeneticTeachingClass c1, GeneticTeachingClass c2) {
    removeFromSchedule(c1);
    removeFromSchedule(c2);
    return scheduleClassRandomly(c1) && scheduleClassRandomly(c2);
  }

  //TODO
  private void removeFromSchedule(GeneticTeachingClass c) {
    scheduledClasses.entrySet()
        .stream()
        .filter(e -> e.getValue().stream().anyMatch(cl -> cl.getGeneticTeachingClass() == c))
        .findFirst()
        .ifPresentOrElse(e ->
                e.getValue().stream()
                    .filter(v -> v.getGeneticTeachingClass() == c)
                    .findFirst()
                    .ifPresentOrElse(sc -> {
                          e.getValue().remove(sc);
                          timeslotRooms.merge(e.getKey(), AvailableRooms.of(sc.getRoom()), AvailableRooms::merge);
                        },
                        () -> {
                          throw new RuntimeException("Failed to remove class");
                        }),
            () -> {
              throw new RuntimeException("Failed to remove class");
            });
  }

  @Override
  public Chromosome prototype() {
    return new Chromosome(scheduledClasses, timeslotRooms);
  }
}
