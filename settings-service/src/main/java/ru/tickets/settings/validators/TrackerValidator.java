package ru.tickets.settings.validators;

import org.springframework.stereotype.Component;
import ru.tickets.api.exceptions.ValidationException;
import ru.tickets.api.tracker.TrackerDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrackerValidator {
    public void validate(TrackerDto trackerDto) {
        List<String> errors = new ArrayList<>();
        if (trackerDto.getTitle().isBlank()) {
            errors.add("Имя трекера не может иметь пустое название");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
