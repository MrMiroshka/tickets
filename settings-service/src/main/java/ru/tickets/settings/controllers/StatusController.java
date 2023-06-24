package ru.tickets.settings.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tickets.api.dto.tracker.StatusDto;
import ru.tickets.api.exceptions.ResourceNotFoundException;
import ru.tickets.settings.converters.StatusConverter;
import ru.tickets.settings.data.Status;
import ru.tickets.settings.servicies.StatusService;
import ru.tickets.settings.validators.StatusValidator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/status")
@RequiredArgsConstructor
@Slf4j
public class StatusController {

    private final StatusService statusService;
    private final StatusConverter statusConverter;

    private final StatusValidator statusValidator;


    /**
     * Находит статус по его id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Optional<StatusDto> findStatusById(@PathVariable Long id) {
        Status status = statusService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Такой статус не найден  id =  " + id));
        return Optional.ofNullable(statusConverter.entityToDto(status));
    }


    /**
     * Создает новый cтатус, при удачном добавлении возвращает добавленный статус
     *
     * @param statusDto - сериализированный из json в объект StatusDto
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<StatusDto> postStatus(@RequestBody StatusDto statusDto) {
        statusValidator.validate(statusDto);
        statusDto.setId(null);
        Status status = this.statusService.addStatus(statusConverter.dtoToEntity(statusDto));
        return Optional.ofNullable(statusConverter.entityToDto(status));
    }

    /**
     * Изменяет статус по его id
     *
     * @param statusDto - сериализированный из json в объект StatusDto с новыми данными
     * @return - возвращает измененный статус
     */
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<StatusDto> updateStatus(@RequestBody StatusDto statusDto) {
        statusValidator.validate(statusDto);
        return Optional.ofNullable(statusConverter.entityToDto
                (this.statusService.changeStatus
                        (statusConverter.dtoToEntity(statusDto))));
    }

    /**
     * Возвращает весь список статус
     *
     * @return - возвращает список статусов, если список существует. Иначе Optional<> содержащий Null
     */
    @GetMapping("/findAll")
    public Optional<List<StatusDto>> findStatusAll() {
        List<Status> status = statusService.findAll();
        return Optional.ofNullable(status.stream().map(t -> statusConverter.entityToDto(t)).toList());
    }

    /**
     * Возвращает пагинированный список статусов с запрашиваемым именем
     *
     * @param page        - номер страницы
     * @param nameStatus - имя статуса или его часть
     * @param pageSize    - количество элементов на странице
     * @return
     */
    @GetMapping
    public Page<StatusDto> getStatus(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "name_status", required = false) String nameStatus,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        if (page < 1) {
            page = 1;
        }

        return statusService.find(nameStatus, page, pageSize).map(p -> statusConverter.entityToDto(p));

    }

    /**
     * Удаляет статус по его id
     *
     * @param id
     */
    @GetMapping("/delete/{id}")
    public void deleteStatusById(@PathVariable Long id) {
        this.statusService.deleteById(id);
        //TODO: реализовать проверку - есть ли уже записи в этом трекере с таким статусом
        // и если таковые имеются запретить удаление
    }


    @GetMapping("/default")
    public  Optional<StatusDto> getDefaultStatus(){
        return Optional.ofNullable(statusConverter.entityToDto(statusService.findDefaultStatus()));
    }
}
