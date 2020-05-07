package com.waytoodanny.timetable.genetic.algorithm;

import com.waytoodanny.timetable.genetic.domain.Parents;
import com.waytoodanny.timetable.genetic.domain.Population;

import java.util.Collection;
import java.util.function.Function;

public interface NextGenParentsSupplier
    extends Function<Population, Collection<Parents>> {

}
