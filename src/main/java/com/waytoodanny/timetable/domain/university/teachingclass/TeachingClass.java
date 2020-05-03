package com.waytoodanny.timetable.domain.university.teachingclass;

import com.waytoodanny.timetable.domain.university.Room;
import com.waytoodanny.timetable.domain.university.StudentGroup;
import com.waytoodanny.timetable.domain.university.Teacher;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public interface TeachingClass {

    //TODO copiesForWeek
    List<? extends TeachingClass> singleWeekClasses();

    Predicate<Room> roomRequirements();

    Set<Teacher> involvedTeachers();

    Set<StudentGroup> involvedStudentGroups();

    boolean hasCommonTeachers(TeachingClass other);

    boolean hasCommonStudentGroups(TeachingClass other);
}
