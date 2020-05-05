package com.waytoodanny.timetable.service.generation.genetics.impl.parentsprovider;

import com.waytoodanny.timetable.configuration.properties.GeneticsProperties;
import com.waytoodanny.timetable.service.generation.genetics.NextGenParentsSupplier;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class TournamentSelection implements NextGenParentsSupplier {

  private final GeneticsProperties geneticsProperties;
  private final Random random;

  @Override
  public Collection<Parents> apply(Population population) {
    return IntStream.range(0, geneticsProperties.getPopulationSize())
        .mapToObj(i -> parents(population))
        .collect(toList());
  }

  private Parents parents(Population population) {
    EvaluatedChromosome parent1;
    EvaluatedChromosome parent2;
    do {
      parent1 = bestOf(selectionCandidates(population));
      parent2 = bestOf(selectionCandidates(population));
    } while (parent1 == parent2);

    return new Parents(parent1, parent2);
  }

  private Set<EvaluatedChromosome> selectionCandidates(Population pop) {
    var result = new HashSet<EvaluatedChromosome>();
    int size = pop.size();
    do {
      result.add(pop.get(random.nextInt(size)));
    } while (result.size() < geneticsProperties.getTournamentSelectionSize());

    return result;
  }

  private EvaluatedChromosome bestOf(Set<EvaluatedChromosome> candidates) {
    return candidates.stream()
        .max(Comparator.comparingInt(EvaluatedChromosome::fitnessValue))
        .orElseThrow(() -> new IllegalArgumentException("No candidates for parenting present"));
  }
}
