package com.waytoodanny.timetable.service.generation.genetics.impl.parentsprovider;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.service.generation.genetics.NextGenParentsSupplier;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Random;

@RequiredArgsConstructor
public class TournamentSelection implements NextGenParentsSupplier {

  private final GeneticsProperties geneticsProperties;
  private final Random random;

  @Override
  public Collection<Parents> apply(Population population) {
    return null;
//    return IntStream.rangeClosed(1, geneticsProperties.getPopulationSize())
//        .mapToObj(i -> parents(population))
//        .collect(toList());
  }
//
//  private Parents parents(Population population) {
//    Chromosome parent1;
//    Chromosome parent2;
//    do {
//      parent1 = bestOf(selectionCandidates(population));
//      parent2 = bestOf(selectionCandidates(population));
//    } while (parent1 == parent2);
//
//    return new Parents(parent1, parent2);
//  }
//
//  d
//
//  private Chromosome bestOf(Set<Chromosome> candidates) {
//    assert !candidates.isEmpty();
//
//    return candidates.stream()
//        .max(comparingInt(Chromosome::fitnessValue))
//        .orElseThrow(() -> new IllegalArgumentException("No candidates for parenting present"));
//  }
}
