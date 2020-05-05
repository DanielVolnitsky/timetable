package com.waytoodanny.timetable.configuration.properties;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Accessors(chain = true) //TODO make global rule
@ConfigurationProperties("timetable.generation.algorithm.genetics")
public class GeneticsProperties {
  private int populationSize;
  private double crossoverRate;
  private int tournamentSelectionSize;
  private int eliteChromosomesNumber;
  private double mutationRate;
}
