package com.waytoodanny.timetable.genetic.algorithm.impl.parentsprovider;

import com.waytoodanny.timetable.domain.Parents;
import com.waytoodanny.timetable.domain.Population;
import com.waytoodanny.timetable.domain.chromosome.EvaluatedChromosome;
import com.waytoodanny.timetable.genetic.GeneticsProperties;
import com.waytoodanny.timetable.genetic.algorithm.NextGenParentsSupplier;
import lombok.RequiredArgsConstructor;

import java.util.*;
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
