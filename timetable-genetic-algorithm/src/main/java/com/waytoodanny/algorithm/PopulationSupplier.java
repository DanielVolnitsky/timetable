package com.waytoodanny.algorithm;

import com.waytoodanny.GeneticInputData;
import com.waytoodanny.entity.Population;

import java.util.function.Function;

public interface PopulationSupplier
    extends Function<GeneticInputData, Population> {

}
