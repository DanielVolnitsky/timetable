package com.waytoodanny.timetable.genetic.algorithm;

import com.waytoodanny.timetable.domain.chromosome.EvaluatedChromosome;

import java.util.function.UnaryOperator;

public interface TimetablePostProcessor extends UnaryOperator<EvaluatedChromosome> {
}
