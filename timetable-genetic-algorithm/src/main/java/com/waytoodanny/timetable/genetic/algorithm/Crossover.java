package com.waytoodanny.timetable.genetic.algorithm;


import com.waytoodanny.timetable.domain.GeneticInputData;
import com.waytoodanny.timetable.domain.Parents;
import com.waytoodanny.timetable.domain.Population;

import java.util.Collection;
import java.util.function.BiFunction;

public interface Crossover
    extends BiFunction<Collection<Parents>, GeneticInputData, Population> {

}
