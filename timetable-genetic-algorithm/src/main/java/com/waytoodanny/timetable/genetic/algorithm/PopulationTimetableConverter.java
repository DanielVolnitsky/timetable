package com.waytoodanny.timetable.genetic.algorithm;

import com.waytoodanny.timetable.Timetable;
import com.waytoodanny.timetable.genetic.domain.chromosome.EvaluatedChromosome;
import org.springframework.core.convert.converter.Converter;

public interface PopulationTimetableConverter extends Converter<EvaluatedChromosome, Timetable> {
}
