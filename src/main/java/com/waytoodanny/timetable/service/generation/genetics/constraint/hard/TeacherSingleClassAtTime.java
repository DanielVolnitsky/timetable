package com.waytoodanny.timetable.service.generation.genetics.constraint.hard;

import com.waytoodanny.timetable.domain.university.Teacher;
import com.waytoodanny.timetable.domain.university.teachingclass.TeachingClass;
import com.waytoodanny.timetable.service.generation.genetics.constraint.HardConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.FitnessFunction;
import com.waytoodanny.timetable.service.generation.genetics.entity.SettledClass;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.Chromosome;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TeacherSingleClassAtTime implements HardConstraint {

    @Override
    public FitnessFunction fitness(Chromosome chromosome, FitnessFunction initial) {
        return chromosome.getScheduledClasses()
                .values().stream()
                .map(this::noClashes)
                .filter(noClashes -> !noClashes)
                .findAny()
                .map(clashes -> initial)
                .orElseGet(() -> initial.plus(this.weight()));
    }

    private boolean noClashes(List<SettledClass> classes) {
        List<Teacher> teachers = classes.stream()
                .map(SettledClass::getTeachingClass)
                .map(TeachingClass::involvedTeachers)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return new HashSet<>(teachers).size() == teachers.size();
    }
}
