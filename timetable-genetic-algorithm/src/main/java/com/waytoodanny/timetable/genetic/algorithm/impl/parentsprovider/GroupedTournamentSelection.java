package com.waytoodanny.timetable.genetic.algorithm.impl.parentsprovider;

import com.waytoodanny.timetable.domain.Parents;
import com.waytoodanny.timetable.domain.Population;
import com.waytoodanny.timetable.domain.chromosome.EvaluatedChromosome;
import com.waytoodanny.timetable.genetic.GeneticProperties;
import com.waytoodanny.timetable.genetic.algorithm.NextGenParentsSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Component
public class GroupedTournamentSelection implements NextGenParentsSupplier {

  private final GeneticProperties geneticProperties;
  private final Random random;

  @Override
  public Collection<Parents> apply(Population population) {
    return IntStream.range(0, geneticProperties.getPopulationSize())
        .mapToObj(i -> parents(population))
        .collect(toList());
  }

  private Parents parents(Population population) {
    Map<Integer, List<EvaluatedChromosome>> groupedByFitness =
        population.stream().collect(groupingBy(EvaluatedChromosome::fitnessValue));

    List<Integer> fitnessValues = new ArrayList<>(groupedByFitness.keySet());
    int selectionSize =
        Math.min(fitnessValues.size(), geneticProperties.getTournamentSelectionSize());

    EvaluatedChromosome parent1;
    EvaluatedChromosome parent2;
    do {
      parent1 = randomCandidate(groupedByFitness,
          candidateFitness(fitnessValues, selectionSize));

      parent2 = randomCandidate(groupedByFitness,
          candidateFitness(fitnessValues, selectionSize));
    } while (parent1 == parent2);

    return new Parents(parent1, parent2);
  }

  private Integer candidateFitness(List<Integer> fitnessValues, int selectionSize) {
    return fitnessValues.get(random.nextInt(selectionSize));
  }

  private EvaluatedChromosome randomCandidate(Map<Integer, List<EvaluatedChromosome>> groupedByFitness,
                                              Integer fitnessValue) {
    List<EvaluatedChromosome> chs = groupedByFitness.get(fitnessValue);
    return chs.get(random.nextInt(chs.size()));
  }
}
