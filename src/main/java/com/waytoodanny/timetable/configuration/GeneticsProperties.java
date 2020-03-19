package com.waytoodanny.timetable.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("timetable.generation.algorithm.genetics")
public class GeneticsProperties {
  private double mutationRate;
}
