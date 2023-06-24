package ru.tickets.settings.validators;

import org.springframework.stereotype.Component;
import ru.tickets.api.dto.tracker.StatusDto;
import ru.tickets.api.exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatusValidator {
    public void validate(StatusDto statusDto) {
        List<String> errors = new ArrayList<>();
        if (statusDto.getTitle().isBlank()) {
            errors.add("Имя статуса не может иметь пустое название");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
