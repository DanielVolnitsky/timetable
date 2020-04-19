package com.waytoodanny.timetable.service.generation.genetics;

import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;

import java.util.function.BiFunction;

public interface Mutation extends BiFunction<Population, InputData, Population> {
}
