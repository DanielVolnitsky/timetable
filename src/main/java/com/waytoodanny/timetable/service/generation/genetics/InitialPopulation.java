package com.waytoodanny.timetable.service.generation.genetics;

import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;

public interface InitialPopulation {
  Population from(InputData inputData);
}
