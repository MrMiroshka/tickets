package ru.tickets.settings.servicies;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tickets.api.dto.tracker.TrackerDtoStandart;
import ru.tickets.api.exceptions.ValidationException;
import ru.tickets.settings.repositories.TrackerDao;
import ru.tickets.settings.repositories.specifications.TrackerSpecifications;
import ru.tickets.settings.data.Tracker;
import ru.tickets.api.exceptions.ResourceNotFoundException;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Lazy
public class TrackerService {
    private final TrackerDao trackerDao;

    public List<Tracker> findAll() {
        return trackerDao.findAll();
    }

    public Page<Tracker> find(String nameTracker, Integer page, Integer pageSize) {
        Specification<Tracker> spec = Specification.where(null);
        if (nameTracker != null) {
            spec = spec.and(TrackerSpecifications.nameLike(nameTracker));
        }
        return this.trackerDao.findAll(spec, PageRequest.of(page - 1, pageSize));

    }


    public Optional<Tracker> findById(Long id) {
        return Optional.ofNullable(trackerDao.findById(id).orElseThrow(() -> new ResourceNotFoundException
                ("Такой трэкер не найден  id =  " + id)));
    }

    public Tracker addTracker(Tracker tracker) {
        return this.trackerDao.save(tracker);
    }

    public void deleteById(Long id) {
        this.trackerDao.deleteById(id);
        // TODO: допилить удаление - если задачи есть у трекера, то запретить удаление.
        //  Как альтернатива сделать поле visiable - в том случае если есть задачи у трекера

    }


    @Transactional
    public Tracker changeTracker(Tracker tracker) {
        List<String> errors = new ArrayList<>();
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Tracker trackerChange = this.trackerDao.findById(tracker.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Такой трэкер не найден id - " + tracker.getId()));
        if (!tracker.getTitle().isBlank()) {
            if (tracker.getTitle().length() > 1) {
                trackerChange.setTitle(tracker.getTitle());
            } else {
                errors.add("Имя трекера должно быть больше чем 1 символ");
            }
        } else {
            errors.add("Имя трекера не может иметь пустое название");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        return trackerChange;
    }
}
