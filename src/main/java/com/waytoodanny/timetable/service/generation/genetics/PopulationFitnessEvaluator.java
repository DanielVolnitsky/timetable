package com.waytoodanny.timetable.service.generation.genetics;

import com.waytoodanny.timetable.service.generation.genetics.entity.EvaluatedPopulation;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;

import java.util.function.Function;

public interface PopulationFitnessEvaluator extends Function<Population, EvaluatedPopulation> {

}
