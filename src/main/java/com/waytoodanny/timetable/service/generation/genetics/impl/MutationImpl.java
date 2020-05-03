package com.waytoodanny.timetable.service.generation.genetics.impl;

import com.waytoodanny.timetable.configuration.GeneticsProperties;
import com.waytoodanny.timetable.domain.timetable.InputData;
import com.waytoodanny.timetable.domain.university.teachingclass.TeachingClass;
import com.waytoodanny.timetable.service.generation.genetics.Mutation;
import com.waytoodanny.timetable.service.generation.genetics.constraint.ScheduleConstraint;
import com.waytoodanny.timetable.service.generation.genetics.entity.Population;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.Chromosome;
import com.waytoodanny.timetable.service.generation.genetics.entity.chromosome.EvaluatedChromosome;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Component
public class MutationImpl implements Mutation {

    private final GeneticsProperties geneticsProperties;
    private final Random random;
    private final Collection<ScheduleConstraint> scheduleConstraints;

    @Override
    public Population apply(Population population, InputData data) {
        return new Population(
                population.stream()
                        .map(c -> mutationResult(c, data))
                        .collect(toList()));
    }

    private EvaluatedChromosome mutationResult(EvaluatedChromosome evaluatedChromosome, InputData data) {
        if (Math.random() > geneticsProperties.getMutationRate()) {
            return evaluatedChromosome;
        }
        return new EvaluatedChromosome(mutatedChromosome(evaluatedChromosome, data), scheduleConstraints);
    }

    private Chromosome mutatedChromosome(EvaluatedChromosome evaluatedChromosome,
                                         InputData data) {
        List<TeachingClass> classes = data.classesToScheduleForWeek();

        Chromosome chromosomeCopy;
        boolean successfullyMutated;
        do {
            chromosomeCopy = evaluatedChromosome.getChromosome().copy();

            TeachingClass randomClass1;
            TeachingClass randomClass2;
            do {
                randomClass1 = classes.get(random.nextInt(classes.size()));
                randomClass2 = classes.get(random.nextInt(classes.size()));
            } while (randomClass1 == randomClass2);

            successfullyMutated = chromosomeCopy.reschedule(randomClass1, randomClass2);
        } while (!successfullyMutated);

        return chromosomeCopy;
    }
}
