package com.waytoodanny.timetable.domain.timetable;

import com.waytoodanny.timetable.domain.university.AvailableRooms;
import com.waytoodanny.timetable.service.generation.genetics.entity.teachingclass.TeachingClass;
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
    private final AvailableRooms rooms;

    private List<TeachingClass> singleWeekClasses;

    public List<TeachingClass> classesToScheduleForWeek() {
        if (nonNull(singleWeekClasses)) {
            return singleWeekClasses;
        }
        singleWeekClasses = teachingClasses.stream()
                .flatMap(c -> c.classCopiesForWeeklyScheduling().stream())
                .collect(toList());
        return singleWeekClasses;
    }
}
