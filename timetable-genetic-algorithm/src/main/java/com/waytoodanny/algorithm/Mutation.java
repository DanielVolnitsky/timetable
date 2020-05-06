package com.waytoodanny.algorithm;

import com.waytoodanny.GeneticInputData;
import com.waytoodanny.entity.Population;

import java.util.function.BiFunction;

public interface Mutation
    extends BiFunction<Population, GeneticInputData, Population> {

}
