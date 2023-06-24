package ru.tickets.settings.servicies;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tickets.api.exceptions.ResourceNotFoundException;
import ru.tickets.api.exceptions.ValidationException;
import ru.tickets.settings.data.Priority;
import ru.tickets.settings.repositories.PriorityDao;
import ru.tickets.settings.repositories.specifications.PrioritySpecifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Lazy
public class PriorityService {
    private final PriorityDao priorityDao;

    public List<Priority> findAll() {
        return priorityDao.findAll();
    }

    public Page<Priority> find(String namePriority, Integer page, Integer pageSize) {
        Specification<Priority> spec = Specification.where(null);
        if (namePriority != null) {
            spec = spec.and(PrioritySpecifications.nameLike(namePriority));
        }
        return this.priorityDao.findAll(spec, PageRequest.of(page - 1, pageSize));

    }


    public Optional<Priority> findById(Long id) {
        return Optional.ofNullable(priorityDao.findById(id).orElseThrow(() -> new ResourceNotFoundException
                ("Такой приоритет не найден  id =  " + id)));
    }

    public Priority addPriority(Priority priority) {
        return this.priorityDao.save(priority);
    }

    public void deleteById(Long id) {
        this.priorityDao.deleteById(id);
        // TODO: допилить удаление - если задачи есть у трекера c таким приоритетом, то запретить удаление.
        //  Как альтернатива сделать поле visiable - в том случае если есть задачи у трекера

    }


    @Transactional
    public Priority changePriority(Priority priority) {
        List<String> errors = new ArrayList<>();
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Priority priorityChange = this.priorityDao.findById(priority.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Такой приоритет не найден id - " + priority.getId()));
        if (!priority.getTitle().isBlank()) {
            if (priority.getTitle().length() > 3) {
                priorityChange.setTitle(priority.getTitle());
            } else {
                errors.add("Имя приоритета должно быть больше чем 3 символа");
            }
        } else {
            errors.add("Имя приоритета не может иметь пустое название");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        return priorityChange;
    }
}
