package com.waytoodanny.timetable.service.generation.genetics.impl.parentsprovider;

import com.waytoodanny.timetable.service.generation.genetics.NextGenerationParentsProvider;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.nCopies;
import static java.util.Objects.isNull;

//TODO optimize
@RequiredArgsConstructor
public class WheelOfFortune implements NextGenerationParentsProvider {

  private final Population source;
  private final Random random;

  private List<Chromosome> parentBucket;

  @Override
  public Parents get() {
    if (isNull(parentBucket)) {
      init();
      return parents();
    }
    return parents();
  }

  private void init() {
    Integer totalFitness = source.stream()
        .map(Chromosome::fitnessValue)
        .reduce(Integer::sum)
        .orElseThrow(() -> new IllegalArgumentException("No chromosomes with fitness scores provided"));

    this.parentBucket = new ArrayList<>();
    for (Chromosome chromosome : source) {
      double part = chromosome.fitnessValue() / (double) totalFitness * 100;
      parentBucket.addAll(nCopies((int) Math.ceil(part), chromosome));
    }
  }

  private Parents parents() {
    int randomSlot1 = random.nextInt(parentBucket.size());
    Chromosome parent1 = parentBucket.get(randomSlot1);

    Chromosome parent2;
    do {
      int randomSlot2 = random.nextInt(parentBucket.size());
      parent2 = parentBucket.get(randomSlot2);
    } while (parent1 == parent2);

    return new Parents(parent1, parent2);
  }
}
