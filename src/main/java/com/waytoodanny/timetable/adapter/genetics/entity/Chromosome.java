package com.waytoodanny.timetable.adapter.genetics.entity;

import com.waytoodanny.timetable.domain.TeachingClass;

import java.util.ArrayList;
import java.util.List;

public class Chromosome {
  private static final int academicHoursPerDay = 6;
  private static final int daysPerWeek = 5;
  private static final int numberOfRooms = 30;
  private static final int classesPerWeek = academicHoursPerDay * daysPerWeek * numberOfRooms;
  private final List<TeachingClass> classes = new ArrayList<>(classesPerWeek);
  // Number of crossover points of parent's class tables
  int crossoverPointsNumber;
  // Number of classes that is moved randomly by single mutation operation
  int mutationSize;
  // Probability that crossover will occure
  int crossoverProbability;
  // Probability that mutation will occure
  int mutationProbability;
}
