package com.waytoodanny.timetable.genetic.algorithm;

import com.waytoodanny.timetable.domain.GeneticInputData;
import com.waytoodanny.timetable.domain.Population;

import java.util.function.BiFunction;

public interface Mutation
    extends BiFunction<Population, GeneticInputData, Population> {

}
