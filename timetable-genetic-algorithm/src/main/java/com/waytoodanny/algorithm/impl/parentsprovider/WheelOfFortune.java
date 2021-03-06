package com.waytoodanny.algorithm.impl.parentsprovider;

import com.waytoodanny.GeneticsProperties;
import com.waytoodanny.algorithm.NextGenParentsSupplier;
import com.waytoodanny.entity.Population;
import com.waytoodanny.entity.chromosome.EvaluatedChromosome;
import com.waytoodanny.entity.timetable.Parents;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

//TODO optimize
@RequiredArgsConstructor
public class WheelOfFortune implements NextGenParentsSupplier {

  private final Random random;
  private final GeneticsProperties geneticsProperties;

  @Override
  public Collection<Parents> apply(Population population) {
    Map<Integer, List<EvaluatedChromosome>> groupedByFitness =
        population.stream().collect(groupingBy(EvaluatedChromosome::fitnessValue));

    Integer overallFitness =
        groupedByFitness.keySet().stream().reduce(Integer::sum).orElse(0);

    Map<Integer, List<EvaluatedChromosome>> collect = groupedByFitness.entrySet().stream()
        .collect(toMap(e -> (int) Math.floor((double) e.getKey() / overallFitness * 100),
            Map.Entry::getValue,
            (l1, l2) -> {
              var r = new ArrayList<>(l1);
              r.addAll(l2);
              return r;
            }));

    var fortuneEntries = new ArrayList<List<EvaluatedChromosome>>();
    collect.forEach((k, v) -> IntStream.range(0, k).forEach(i -> fortuneEntries.add(v)));

    return IntStream.range(0, geneticsProperties.getPopulationSize())
        .mapToObj(i -> parents(fortuneEntries))
        .collect(toList());
  }

  private Parents parents(List<List<EvaluatedChromosome>> fortuneEntries) {
    EvaluatedChromosome parent1;
    EvaluatedChromosome parent2;
    do {
      parent1 = fortuneParent(fortuneEntries);
      parent2 = fortuneParent(fortuneEntries);
    } while (parent1 == parent2);

    return new Parents(parent1, parent2);
  }

  private EvaluatedChromosome fortuneParent(List<List<EvaluatedChromosome>> fortuneEntries) {
    List<EvaluatedChromosome> fortuneCandidates = fortuneEntries.get(random.nextInt(fortuneEntries.size()));
    return fortuneCandidates.get(random.nextInt(fortuneCandidates.size()));
  }
}
