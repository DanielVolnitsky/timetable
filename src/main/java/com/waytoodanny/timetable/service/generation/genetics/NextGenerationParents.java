package com.waytoodanny.timetable.service.generation.genetics;

import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;

import java.util.Collection;
import java.util.function.Supplier;

public interface NextGenerationParents extends Supplier<Collection<Parents>> {

}
