package com.waytoodanny.timetable.domain.chromosome;

import com.waytoodanny.timetable.domain.Shifts;
import com.waytoodanny.timetable.domain.university.AvailableRooms;
import lombok.NonNull;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Value
@Component
public class ChromosomeFactory {

  Shifts shifts;

  public Chromosome chromosome(@NonNull AvailableRooms rooms,
                               @NonNull Collection<Integer> timeSlotsPerWeek) {
    return new Chromosome(rooms, timeSlotsPerWeek, shifts);
  }
}
