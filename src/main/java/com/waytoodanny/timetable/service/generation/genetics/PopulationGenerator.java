package com.waytoodanny.timetable.service.generation.genetics;

import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;

public interface PopulationGenerator {
  Population population(InputData inputData);
}
