package com.waytoodanny.timetable.service.generation.genetics;

import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;

import java.util.Collection;
import java.util.function.Function;

public interface NextGenParents extends Function<Population, Collection<Parents>> {

}
