package com.waytoodanny.algorithm;


import com.waytoodanny.GeneticInputData;
import com.waytoodanny.entity.Population;
import com.waytoodanny.entity.timetable.Parents;

import java.util.Collection;
import java.util.function.BiFunction;

public interface Crossover
    extends BiFunction<Collection<Parents>, GeneticInputData, Population> {

}
