package com.waytoodanny.timetable.genetic.algorithm;


import com.waytoodanny.timetable.genetic.domain.GeneticInputData;
import com.waytoodanny.timetable.genetic.domain.Parents;
import com.waytoodanny.timetable.genetic.domain.Population;

import java.util.Collection;
import java.util.function.BiFunction;

public interface Crossover
    extends BiFunction<Collection<Parents>, GeneticInputData, Population> {

}
