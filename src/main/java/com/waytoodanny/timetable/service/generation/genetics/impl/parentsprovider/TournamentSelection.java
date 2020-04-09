package com.waytoodanny.timetable.service.generation.genetics.impl.parentsprovider;

import com.waytoodanny.timetable.service.generation.genetics.NextGenerationParentsProvider;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.util.Comparator.comparingInt;

@RequiredArgsConstructor
public class TournamentSelection implements NextGenerationParentsProvider {

  private final Population source;
  private final int tournamentSelectionSize;
  private final Random random;

  @Override
  public Parents get() {
    Chromosome parent1;
    Chromosome parent2;
    do {
      parent1 = bestOf(selectionCandidates());
      parent2 = bestOf(selectionCandidates());
    } while (parent1 == parent2);

    return new Parents(parent1, parent2);
  }

  private HashSet<Chromosome> selectionCandidates() {
    var candidates = new HashSet<Chromosome>();
    do {
      candidates.add(source.get(random.nextInt(source.size())));
    } while (candidates.size() < tournamentSelectionSize);
    return candidates;
  }

  private Chromosome bestOf(Set<Chromosome> candidates) {
    assert !candidates.isEmpty();

    return candidates.stream()
        .max(comparingInt(Chromosome::fitnessValue))
        .orElseThrow(() -> new IllegalArgumentException("No candidates for parenting present"));
  }
}
