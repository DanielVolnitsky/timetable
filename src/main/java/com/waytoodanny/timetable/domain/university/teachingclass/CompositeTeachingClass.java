package com.waytoodanny.timetable.domain.university.teachingclass;

import com.waytoodanny.timetable.domain.university.ClassType;
import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.StudentGroup;
import com.waytoodanny.timetable.domain.university.Subject;
import com.waytoodanny.timetable.domain.university.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Builder
@ToString(of = {"teachers", "groups", "subject"})
public class CompositeTeachingClass implements TeachingClass {
    private final Subject subject;
    @Singular
    private final Set<Teacher> teachers;
    @Singular
    private final Set<StudentGroup> groups;
    private final ClassType type;

    private final int classesNumber;
    private final int classesPerWeek;

    @Override
    public List<CompositeTeachingClass> classCopiesForWeeklyScheduling() {
        return IntStream.range(0, classesPerWeek)
                .mapToObj(i -> copy())
                .collect(toList());
    }

    public Predicate<Room> roomRequirements() {
        return room -> studentsOverall() <= room.getCapacity();
    }

    @Override
    public Set<Teacher> involvedTeachers() {
        return teachers;
    }

    @Override
    public Set<StudentGroup> involvedStudentGroups() {
        return groups;
    }

    @Override
    public boolean hasCommonTeachers(TeachingClass other) {
        var otherTeachers = other.involvedTeachers();
        return this.involvedTeachers().stream().anyMatch(otherTeachers::contains);
    }

    @Override
    public boolean hasCommonStudentGroups(TeachingClass other) {
        var otherGroups = other.involvedStudentGroups();
        return this.involvedStudentGroups().stream().anyMatch(otherGroups::contains);
    }

    //TODO cache
    private Integer studentsOverall() {
        return groups.stream()
                .map(StudentGroup::getStudentsNumber)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private CompositeTeachingClass copy() {
        return new CompositeTeachingClass(
                subject, teachers, groups, type, classesNumber, classesPerWeek);
    }
}
