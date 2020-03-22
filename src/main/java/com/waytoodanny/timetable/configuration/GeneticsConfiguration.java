package com.waytoodanny.timetable.configuration;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Accessors(chain = true) //TODO make global rule
@ConfigurationProperties("timetable.generation.algorithm.genetics")
public class GeneticsConfiguration {
  private double mutationRate;
  private int populationSize;
}
