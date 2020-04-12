package com.waytoodanny.timetable.service.generation.genetics.impl.parentsprovider;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.service.generation.genetics.NextGenParents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Parents;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class TournamentSelection implements NextGenParents {

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
//  private HashSet<Chromosome> selectionCandidates(Population population) {
//    int size = population.size();
//    var candidates = new HashSet<Chromosome>();
//    do {
//      candidates.add(population.get(random.nextInt(size)));
//    } while (candidates.size() < geneticsProperties.getTournamentSelectionSize());
//    return candidates;
//  }
//
//  private Chromosome bestOf(Set<Chromosome> candidates) {
//    assert !candidates.isEmpty();
//
//    return candidates.stream()
//        .max(comparingInt(Chromosome::fitnessValue))
//        .orElseThrow(() -> new IllegalArgumentException("No candidates for parenting present"));
//  }
}
