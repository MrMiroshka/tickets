package ru.tickets.settings.controllers;

import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tickets.api.tracker.TrackerDto;
import ru.tickets.api.tracker.fabrica.TrackerDtoType;
import ru.tickets.api.exceptions.ResourceNotFoundException;
import ru.tickets.settings.converters.TrackerConverter;
import ru.tickets.settings.data.Tracker;
import ru.tickets.settings.servicies.TrackerService;
import ru.tickets.settings.validators.TrackerValidator;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tracker")
@RequiredArgsConstructor
public class TrackerController {
    private final TrackerService trackerService;
    private final TrackerConverter trackerConverter;

    private final TrackerValidator trackerValidator;


    /**
     * Находит трекер по его id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Optional<TrackerDto> findTrackerById(@PathVariable Long id) {
        Tracker tracker = trackerService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Такой трекер не найден  id =  " + id));
        return Optional.ofNullable(trackerConverter.entityToDto(tracker));
    }


    /**
     * Создает новый трекер, при удачном добавлении возвращает добавленный трекер
     *
     * @param jsonTracker - json c данными для создания трекера
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<TrackerDto> postTracker(@RequestParam(name = "typeClient", defaultValue = "WEB") TrackerDtoType typeClient,
                                            @RequestBody String jsonTracker/*TrackerDto trackerDto*/) throws JSONException {
        TrackerDto trackerDto = trackerConverter.JsonToDto(typeClient, jsonTracker);
        trackerValidator.validate(trackerDto);
        trackerDto.setId(null);
        Tracker tracker = this.trackerService.addTracker(trackerConverter.dtoToEntity(trackerDto));
        return Optional.ofNullable(trackerConverter.entityToDto(tracker));
    }

    /**
     * Изменяет трекер по его id
     * @param typeClient тип клиента (WEB,MOBILE)
     * @param jsonTracker трекер в формате json
     * @return Возвращаем измененный объект
     * @throws JSONException
     */
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<TrackerDto> updateTracker(@RequestParam(name = "typeClient", defaultValue = "WEB") TrackerDtoType typeClient,
                                              @RequestBody String jsonTracker  /*TrackerDto trackerDto*/) throws JSONException {
        TrackerDto trackerDto = trackerConverter.JsonToDto(typeClient, jsonTracker);
        trackerValidator.validate(trackerDto);
        return Optional.ofNullable(trackerConverter.entityToDto
                (this.trackerService.changeTracker
                        (trackerConverter.dtoToEntity(trackerDto))));
    }

    /**
     * Возвращает весь список трекеров
     *
     * @return
     */
    @GetMapping("/findAll")
    public Optional<List<TrackerDto>> findTrackerAll() {
        List<Tracker> tracker = trackerService.findAll();
        return Optional.ofNullable(tracker.stream().map(t -> trackerConverter.entityToDto(t)).toList());
    }

    /**
     * Возвращает пагинированный список трекеров с запрашиваемым именем
     *
     * @param page        - номер страницы
     * @param nameTracker - имя трекера или его часть
     * @param pageSize    - количество элементов на странице
     * @return
     */
    @GetMapping
    public Page<TrackerDto> getTrackers(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "name_tracker", required = false) String nameTracker,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        if (page < 1) {
            page = 1;
        }

        return  trackerService.find(nameTracker,page,pageSize).map(p->trackerConverter.entityToDto(p));

    }

    /**
     * Удаляет трекер по его id
     *
     * @param id
     */
    @GetMapping("/delete/{id}")
    public void deleteTrackerById(@PathVariable Long id) {
        this.trackerService.deleteById(id);
        //TODO: реализовать проверку - есть ли уже записи в этом трекере и если таковые имеются запретить удаление
    }

}
