package ru.tickets.settings.validators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tickets.api.dto.tracker.PriorityDto;
import ru.tickets.api.exceptions.ValidationException;
import ru.tickets.settings.servicies.PriorityService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriorityValidator {
    private final PriorityService priorityService;

    public void validate(PriorityDto priorityDto) {
        List<String> errors = new ArrayList<>();
        if (priorityDto.getTitle().isBlank()) {
            errors.add("Имя приоритета не может иметь пустое название");
        }

        if (priorityDto.getPriorityValue() == null || priorityDto.getPriorityValue().compareTo((short) 1)<0) {
            errors.add("Значение приоритета не может быть пустым или меньше 1");
        }

        if (!priorityService.findAll().stream().filter((obj) -> {
                    return obj.getPriorityValue().equals(priorityDto.getPriorityValue());
                }).findFirst()
                .isEmpty()) {
            errors.add("Приоритет с таким значением уже существует!");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
