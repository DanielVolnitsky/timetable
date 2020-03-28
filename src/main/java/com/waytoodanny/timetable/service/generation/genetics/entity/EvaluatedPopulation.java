package com.waytoodanny.timetable.service.generation.genetics.entity;

import lombok.Value;

import java.util.List;

@Value
public class EvaluatedPopulation {
  List<Chromosome> evaluatedChromosomes;
}
