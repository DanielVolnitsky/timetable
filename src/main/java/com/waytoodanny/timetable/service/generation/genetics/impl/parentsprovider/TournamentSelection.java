package com.waytoodanny.timetable.service.generation.genetics.impl.parentsprovider;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.service.generation.genetics.NextGenerationParents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class TournamentSelection implements NextGenerationParents {

  private final Population source;
  private final GeneticsProperties geneticsProperties;
  private final Random random;

  @Override
  public Collection<Parents> get() {
    return IntStream.rangeClosed(1, geneticsProperties.populationSize())
        .mapToObj(i -> parents())
        .collect(toList());
  }

  private Parents parents() {
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
    } while (candidates.size() < geneticsProperties.tournamentSelectionSize());
    return candidates;
  }

  private Chromosome bestOf(Set<Chromosome> candidates) {
    assert !candidates.isEmpty();

    return candidates.stream()
        .max(comparingInt(Chromosome::fitnessValue))
        .orElseThrow(() -> new IllegalArgumentException("No candidates for parenting present"));
  }
}
