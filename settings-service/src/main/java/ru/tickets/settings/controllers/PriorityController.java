package ru.tickets.settings.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tickets.api.dto.tracker.PriorityDto;
import ru.tickets.api.exceptions.ResourceNotFoundException;
import ru.tickets.settings.converters.PriorityConverter;
import ru.tickets.settings.data.Priority;
import ru.tickets.settings.servicies.PriorityService;
import ru.tickets.settings.validators.PriorityValidator;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/priority")
@RequiredArgsConstructor
@Slf4j
public class PriorityController {
    private final PriorityService priorityService;
    private final PriorityConverter priorityConverter;
    private final PriorityValidator priorityValidator;


    /**
     * Находит приоритет по его id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Optional<PriorityDto> findPriorityById(@PathVariable Long id) {
        Priority priority = priorityService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Такой приоритет не найден  id =  " + id));
        return Optional.ofNullable(priorityConverter.entityToDto(priority));
    }


    /**
     * Создает новый приоритет, при удачном добавлении возвращает добавленный приоритет
     *
     * @param priorityDto - сериализированный из json в объект priorityDto
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<PriorityDto> postPriority(@RequestBody PriorityDto priorityDto) {
        priorityValidator.validate(priorityDto);
        priorityDto.setId(null);
        Priority priority = this.priorityService.addPriority(priorityConverter.dtoToEntity(priorityDto));
        return Optional.ofNullable(priorityConverter.entityToDto(priority));
    }

    /**
     * Изменяет приоритета по его id
     *
     * @param priorityDto - сериализированный из json в объект priorityDto с новыми данными
     * @return - возвращает измененный приоритет
     */
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<PriorityDto> updatePriority(@RequestBody PriorityDto priorityDto) {
        priorityValidator.validate(priorityDto);
        return Optional.ofNullable(priorityConverter.entityToDto
                (this.priorityService.changePriority
                        (priorityConverter.dtoToEntity(priorityDto))));
    }

    /**
     * Возвращает весь список приоритетов
     *
     * @return - возвращает список приоритетов, если список существует. Иначе Optional<> содержащий Null
     */
    @GetMapping("/findAll")
    public Optional<List<PriorityDto>> findPriorityAll() {
        List<Priority> priority = priorityService.findAll();
        return Optional.ofNullable(priority.stream().map(t -> priorityConverter.entityToDto(t)).toList());
    }

    /**
     * Возвращает пагинированный список приоритетов с запрашиваемым именем
     *
     * @param page         - номер страницы
     * @param namePriority - имя приоритета или его часть
     * @param pageSize     - количество элементов на странице
     * @return
     */
    @GetMapping
    public Page<PriorityDto> getPriority(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "namePriority", required = false) String namePriority,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        if (page < 1) {
            page = 1;
        }

        return priorityService.find(namePriority, page, pageSize).map(p -> priorityConverter.entityToDto(p));

    }

    /**
     * Удаляет приоритет по его id
     *
     * @param id
     */
    @GetMapping("/delete/{id}")
    public void deletePriorityById(@PathVariable Long id) {
        this.priorityService.deleteById(id);
        //TODO: реализовать проверку - есть ли уже записи в этом трекере с таким приоритетом
        // и если таковые имеются запретить удаление
    }
}
