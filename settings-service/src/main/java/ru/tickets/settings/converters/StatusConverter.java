package ru.tickets.settings.converters;
import org.springframework.stereotype.Component;
import ru.tickets.api.dto.tracker.StatusDto;
import ru.tickets.settings.data.Status;
import java.time.LocalDateTime;

@Component
public class StatusConverter {
    public Status dtoToEntity(StatusDto statusDto) {
        return new Status(statusDto.getId(), statusDto.getTitle(), statusDto.getDefaultStatus(),LocalDateTime.now(), LocalDateTime.now());
    }

    public StatusDto entityToDto(Status status) {
        return new StatusDto(status.getId(), status.getTitle(),status.getDefaultStatus(),status.getCreatedAt(),status.getUpdatedAt());
    }

}
