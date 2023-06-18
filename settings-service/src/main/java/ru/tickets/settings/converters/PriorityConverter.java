package ru.tickets.settings.converters;

import org.springframework.stereotype.Component;
import ru.tickets.api.dto.tracker.PriorityDto;
import ru.tickets.settings.data.Priority;

import java.time.LocalDateTime;

@Component
public class PriorityConverter {
    public Priority dtoToEntity(PriorityDto priorityDto) {
        return new Priority(priorityDto.getId(), priorityDto.getTitle(), priorityDto.getPriorityValue(), LocalDateTime.now(), LocalDateTime.now());
    }

    public PriorityDto entityToDto(Priority priority) {
        return new PriorityDto(priority.getId(), priority.getTitle(), priority.getPriorityValue(), priority.getCreatedAt(),priority.getUpdatedAt());
    }
}
