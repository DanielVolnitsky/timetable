package com.waytoodanny.algorithm;

import com.waytoodanny.entity.Population;
import com.waytoodanny.entity.timetable.Parents;

import java.util.Collection;
import java.util.function.Function;

public interface NextGenParentsSupplier
    extends Function<Population, Collection<Parents>> {

}
