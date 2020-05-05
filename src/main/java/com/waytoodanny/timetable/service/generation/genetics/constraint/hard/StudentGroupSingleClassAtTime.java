package com.waytoodanny.timetable.service.generation.genetics.constraint.hard;

import com.waytoodanny.timetable.domain.university.StudentGroup;
import com.waytoodanny.timetable.service.generation.genetics.constraint.HardConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.SettledClass;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.teachingclass.TeachingClass;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * No student group can attend more than one class at the same time.
 */
@Component
public class StudentGroupSingleClassAtTime implements HardConstraint {

    @Override
    public FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial) {
        return chromosome.getScheduledClasses().values().stream()
                .map(this::noClashes)
                .filter(noClashes -> !noClashes)
                .findAny()
                .map(clashes -> initial)
                .orElseGet(() -> initial.plus(this.weight()));
    }

    private boolean noClashes(List<SettledClass> classes) {
        List<StudentGroup> groups = classes.stream()
                .map(SettledClass::getTeachingClass)
                .map(TeachingClass::involvedStudentGroups)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return new HashSet<>(groups).size() == groups.size();
    }
}
