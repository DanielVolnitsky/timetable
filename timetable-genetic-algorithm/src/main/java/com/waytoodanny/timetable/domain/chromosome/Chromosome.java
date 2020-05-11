package com.waytoodanny.timetable.domain.chromosome;

import com.waytoodanny.timetable.domain.GeneticTeachingClass;
import com.waytoodanny.timetable.domain.SettledClass;
import com.waytoodanny.timetable.domain.TimeslotClasses;
import com.waytoodanny.timetable.domain.university.AvailableRooms;
import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.util.Prototyped;
import io.vavr.Tuple2;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@EqualsAndHashCode
@ToString(of = "scheduledClasses")
public class Chromosome implements Prototyped<Chromosome> {
  @Getter
  private final Map<Integer, ScheduledClasses> scheduledClasses = new TreeMap<>();
  private final TimeslotRooms timeslotRooms;

  public Chromosome(@NonNull AvailableRooms rooms,
                    @NonNull Collection<Integer> timeSlotsPerWeek) {
    this.timeslotRooms = new TimeslotRooms(timeSlotsPerWeek, rooms);
  }

  private Chromosome(Map<Integer, ScheduledClasses> scheduledClasses,
                     TimeslotRooms timeslotRooms) {
    scheduledClasses.forEach((key, value) -> this.scheduledClasses.put(key, value.prototype()));
    this.timeslotRooms = timeslotRooms.prototype();
  }

  @Override
  public Chromosome prototype() {
    return new Chromosome(scheduledClasses, timeslotRooms);
  }

  //TODO check if possible to schedule?
  public boolean scheduleClass(GeneticTeachingClass tClass, int timeslot) {
    AvailableRooms rooms = timeslotRooms.timeslotRooms(timeslot);
    if (rooms.isEmpty()) {
      return false;
    }

    return rooms.withdrawBestSuitableFor(tClass.roomRequirements())
        .map(r ->
            scheduledClasses.merge(
                timeslot,
                new ScheduledClasses(List.of(new SettledClass(r, tClass))),
                ScheduledClasses::merge))
        .isPresent();
  }

  public boolean scheduleClassRandomly(GeneticTeachingClass tClass) {
    List<Integer> possibleTimeslots = timeslotRooms.appropriateForScheduling(tClass);
    if (possibleTimeslots.isEmpty()) {
      return false;
    }

    int randomSlotIdx = (int) (Math.random() * possibleTimeslots.size());
    Integer randomSlot = possibleTimeslots.get(randomSlotIdx);
    return scheduleClass(tClass, randomSlot);
  }

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

  public boolean reschedule(GeneticTeachingClass c1, GeneticTeachingClass c2) {
    removeFromSchedule(c1);
    removeFromSchedule(c2);
    return scheduleClassRandomly(c1) && scheduleClassRandomly(c2);
  }

  public Collection<ScheduledClasses> scheduledClasses() {
    return scheduledClasses.values();
  }

  public List<TimeslotClasses> timeslotClasses() {
    return scheduledClasses.entrySet().stream()
        .map(e -> new TimeslotClasses(e.getKey(), e.getValue().classes))
        .collect(toList());
  }

  private boolean isPossibleToSchedule(GeneticTeachingClass candidateClass, int timeslot) {
    ScheduledClasses timeslotClasses = scheduledClasses.get(timeslot);
    if (timeslotClasses == null) {
      return true;
    }
    return timeslotClasses.stream()
        .map(SettledClass::getGeneticTeachingClass)
        .noneMatch(
            scheduledClass ->
                scheduledClass.hasCommonStudentGroups(candidateClass)
                    || scheduledClass.hasCommonTeachers(candidateClass));
  }


  private void removeFromSchedule(GeneticTeachingClass classToRemove) {
    Tuple2<Integer, ScheduledClasses> classesForSlot = scheduledClasses.entrySet()
        .stream()
        .filter(e -> e.getValue().containsClass(classToRemove))
        .findFirst()
        .map(e -> new Tuple2<>(e.getKey(), e.getValue()))
        .orElseThrow(() ->
            new RuntimeException("No settled class contained requested teaching class"));

    ScheduledClasses classes = classesForSlot._2();
    SettledClass settledToRemove = classes.instanceWithTeachingClass(classToRemove);
    classes.remove(settledToRemove);
    timeslotRooms.add(classesForSlot._1, settledToRemove.getRoom());
  }

  @AllArgsConstructor
  public static class ScheduledClasses implements Prototyped<ScheduledClasses> {

    //TODO
    @Getter
    private final List<SettledClass> classes;

    public boolean match(Predicate<List<SettledClass>> predicate) {
      return predicate.test(classes);
    }

    private ScheduledClasses merge(ScheduledClasses other) {
      var merged = new ArrayList<SettledClass>();
      merged.addAll(this.classes);
      merged.addAll(other.classes);
      return new ScheduledClasses(merged);
    }

    private boolean containsClass(GeneticTeachingClass toCheck) {
      return classes.stream().anyMatch(cl -> cl.getGeneticTeachingClass() == toCheck);
    }

    private SettledClass instanceWithTeachingClass(GeneticTeachingClass gtc) {
      return stream()
          .filter(cl -> cl.getGeneticTeachingClass() == gtc)
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("Requested class had to be scheduled, but was not"));
    }

    private void remove(SettledClass toRemove) {
      classes.remove(toRemove);
    }

    //TODO
    public Stream<SettledClass> stream() {
      return classes.stream();
    }

    @Override
    public ScheduledClasses prototype() {
      return new ScheduledClasses(new ArrayList<>(this.classes));
    }
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public class TimeslotRooms implements Prototyped<TimeslotRooms> {
    private final Map<Integer, AvailableRooms> timeslotRooms = new HashMap<>();

    public TimeslotRooms(Collection<Integer> timeSlotsPerWeek,
                         AvailableRooms rooms) {
      timeSlotsPerWeek.stream()
          .sorted()
          .forEach(t -> timeslotRooms.put(t, rooms.prototype()));
    }

    private List<Integer> appropriateForScheduling(GeneticTeachingClass tClass) {
      return timeslotRooms.entrySet()
          .stream()
          .filter(e -> e.getValue()
              .findBestSuitableFor(tClass.roomRequirements())
              .isPresent())
          .filter(e -> isPossibleToSchedule(tClass, e.getKey()))
          .map(Map.Entry::getKey)
          .collect(toList());
    }

    @Override
    public TimeslotRooms prototype() {
      TimeslotRooms prototype = new TimeslotRooms();
      prototype.timeslotRooms.putAll(timeslotRooms);
      return prototype;
    }

    public AvailableRooms timeslotRooms(int timeslot) {
      return timeslotRooms.get(timeslot);
    }

    public void add(Integer slot, Room room) {
      timeslotRooms.merge(slot, AvailableRooms.of(room), AvailableRooms::merge);
    }
  }
}
