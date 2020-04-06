package com.waytoodanny.timetable.service.generation.genetics.factory;

import com.waytoodanny.timetable.service.generation.genetics.NextGenerationParentsProvider;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;

import java.util.function.Function;

public interface NextGenerationParentsProviderFactory
    extends Function<Population, NextGenerationParentsProvider> {

}
