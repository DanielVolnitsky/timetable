package com.waytoodanny.timetable.service.generation.genetics.entity.chromosome;

import com.waytoodanny.timetable.domain.university.AvailableRooms;
import com.waytoodanny.timetable.domain.university.teachingclass.TeachingClass;
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

    private boolean canScheduleClass(TeachingClass candidateClass, int timeslot) {
        List<SettledClass> timeslotClasses = scheduledClasses.get(timeslot);
        if (timeslotClasses == null) {
            return true;
        }
        return timeslotClasses.stream()
                .map(SettledClass::getTeachingClass)
                .noneMatch(scheduledClass ->
                        scheduledClass.hasCommonStudentGroups(candidateClass)
                                || scheduledClass.hasCommonTeachers(candidateClass));
    }

    public boolean scheduleClassRandomly(TeachingClass tClass) {
        return timeslotRooms.entrySet()
                .stream()
                //TODO
                .parallel()
                .filter(e -> e.getValue()
                        .getBestSuitableFor(tClass.roomRequirements())
                        .isPresent())
                .filter(e -> canScheduleClass(tClass, e.getKey()))
                .findAny()
                .map(Map.Entry::getKey)
                .map(timeslot -> scheduleClass(tClass, timeslot))
                .orElse(false);
    }

    //TODO decide where parallelization is needed or consequent class scheduling is appropriate decision
    public int timeslotForClass(TeachingClass teachingClass) {
        return scheduledClasses.entrySet().stream()
                .filter(e -> e.getValue().stream()
                        .map(SettledClass::getTeachingClass)
                        .collect(toList())
                        .contains(teachingClass))
                .map(Map.Entry::getKey)
                .findAny().orElse(0);
    }

    private List<SettledClass> mergeLists(List<SettledClass> l1, List<SettledClass> l2) {
        List<SettledClass> r = new ArrayList<>();
        r.addAll(l1);
        r.addAll(l2);
        return r;
    }

    //TODO
    public void reschedule(TeachingClass c1, TeachingClass c2) {
        removeFromSchedule(c1);
        removeFromSchedule(c2);
        scheduleClassRandomly(c1);
        scheduleClassRandomly(c2);
    }

    //TODO
    private void removeFromSchedule(TeachingClass c) {
        scheduledClasses.entrySet()
                .stream()
                .filter(e -> e.getValue().stream().anyMatch(cl -> cl.getTeachingClass() == c))
                .findFirst()
                .ifPresentOrElse(e ->
                                e.getValue().stream()
                                        .filter(v -> v.getTeachingClass() == c)
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
}
