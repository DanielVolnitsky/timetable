package com.waytoodanny.timetable.genetic.algorithm;

import com.waytoodanny.timetable.genetic.domain.GeneticInputData;
import com.waytoodanny.timetable.genetic.domain.Population;

import java.util.function.Function;

public interface InitialPopulationSupplier
    extends Function<GeneticInputData, Population> {

}
