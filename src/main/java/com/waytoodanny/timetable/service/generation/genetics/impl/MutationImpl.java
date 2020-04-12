package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.service.generation.genetics.Mutation;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.Gene;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Component
public class MutationImpl implements Mutation {

  private final GeneticsProperties geneticsProperties;
  private final Random random;

  @Override
  public Population apply(Population population) {
    var result = population.stream().map(this::mutate).collect(toList());
    return new Population(result);
  }

  private Chromosome mutate(Chromosome c) {
    if (Math.random() > geneticsProperties.getMutationRate()) {
      List<Gene> genes = c.getGenes();
      if (genes.size() < 2) {
        return c;
      }
      int rGene1Idx = random.nextInt(genes.size());
      int rGene2Idx;
      do {
        rGene2Idx = random.nextInt(genes.size());
      } while (rGene1Idx == rGene2Idx);
      Gene gene1 = genes.get(rGene1Idx);
      Gene gene2 = genes.get(rGene2Idx);
      gene1.swapClasses(gene2);
    }
    return c;
  }
}
