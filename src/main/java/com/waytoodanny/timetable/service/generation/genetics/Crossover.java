package com.waytoodanny.timetable.service.generation.genetics;

import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;

import java.util.Collection;
import java.util.function.BiFunction;

public interface Crossover extends BiFunction<Collection<Parents>, InputData, Population> {
}
