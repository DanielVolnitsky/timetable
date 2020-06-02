package com.waytoodanny.timetable.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waytoodanny.timetable.TimetableConverter;
import com.waytoodanny.timetable.domain.Weekday;
import com.waytoodanny.timetable.domain.university.ClassType;
import com.waytoodanny.timetable.domain.university.Subject;
import com.waytoodanny.timetable.domain.university.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
@AllArgsConstructor
public class TimetableToJsonConverter implements TimetableConverter<String> {

    private final ObjectMapper jsonMapper;

    @Override
    @SneakyThrows
    public String convert(com.waytoodanny.timetable.Timetable timetable) {
        return jsonMapper.writeValueAsString(
                Timetable.fromDomain(timetable));
    }

    @Data
    private static class Timetable {
        private final List<TeachingSlot> teachingSlots;

        private static Timetable fromDomain(com.waytoodanny.timetable.Timetable domain) {
            return new Timetable(
                    domain.getTeachingSlots().stream()
                            .map(TeachingSlot::fromDomain)
                            .collect(toList()));
        }
    }

    @Data
    private static class TeachingSlot {
        private final TeachingClass teachingClass;
        private final Room room;
        private final int pairNumber;
        private final TimeCoordinates timeCoordinates;

        public static TeachingSlot fromDomain(com.waytoodanny.timetable.domain.TeachingSlot domain) {
            return new TeachingSlot(
                TeachingClass.fromDomain(domain.getTeachingClass()),
                Room.fromDomain(domain.getRoom()),
                domain.getPairNumber(),
                TimeCoordinates.fromDomain(domain.getTimeCoordinates()));
        }
    }

    @Data
    private static class TeachingClass {
        private final Subject subject;
        private final Set<Teacher> teachers;
        private final Set<StudentGroup> groups;
        private final ClassType type;

        public static TeachingClass
        fromDomain(com.waytoodanny.timetable.domain.university.TeachingClass domain) {
            return new TeachingClass(
                    domain.getSubject(),
                    domain.getTeachers(),
                    domain.getGroups().stream().map(StudentGroup::fromDomain).collect(toSet()),
                    domain.getType());
        }
    }

    @Data
    private static class TimeCoordinates {
        private final int weekNumber;
        private final Weekday day;
        private final LocalTime start;
        private final LocalTime end;

        public static TimeCoordinates
        fromDomain(com.waytoodanny.timetable.domain.TimeCoordinates domain) {
            return new TimeCoordinates(
                    domain.getWeekNumber(),
                    domain.getDay(),
                    domain.getStart(),
                    domain.getEnd());
        }
    }

    @Data
    public static class StudentGroup {
        private final String name;

        public static StudentGroup
        fromDomain(com.waytoodanny.timetable.domain.university.StudentGroup domain) {
            return new StudentGroup(domain.getName());
        }
    }

    @Data
    private static class Room {
        private final String name;

        public static Room
        fromDomain(com.waytoodanny.timetable.domain.university.Room domain) {
            return new Room(domain.getName());
        }
    }
}
