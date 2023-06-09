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
import ru.tickets.settings.data.Status;
import ru.tickets.settings.repositories.StatusDao;
import ru.tickets.settings.repositories.specifications.StatusSpecifications;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Lazy
public class StatusService {
    private final StatusDao statusDao;

    public List<Status> findAll() {
        return statusDao.findAll();
    }

    public Status findDefaultStatus() {
        Status status = null;
        try {
            status = statusDao.findAll().stream().filter((obj) -> obj.getDefaultStatus().equals(Boolean.TRUE)).findFirst().get();
        } catch (NoSuchElementException exp) {
            List<String> errorStr = new ArrayList<>();
            errorStr.add("Не установлен статус по умолчанию (который ставится при создании тикета)");
            throw new ValidationException(errorStr);
        }
        
        return status;
    }

    public Page<Status> find(String nameStatus, Integer page, Integer pageSize) {
        Specification<Status> spec = Specification.where(null);
        if (nameStatus != null) {
            spec = spec.and(StatusSpecifications.nameLike(nameStatus));
        }
        return this.statusDao.findAll(spec, PageRequest.of(page - 1, pageSize));

    }


    public Optional<Status> findById(Long id) {
        return Optional.ofNullable(statusDao.findById(id).orElseThrow(() -> new ResourceNotFoundException
                ("Такой статус не найден  id =  " + id)));
    }

    public Status addStatus(Status status) {
        return this.statusDao.save(status);
    }

    public void deleteById(Long id) {
        this.statusDao.deleteById(id);
        // TODO: допилить удаление - если задачи есть у трекера c таким статусом, то запретить удаление.
        //  Как альтернатива сделать поле visiable - в том случае если есть задачи у трекера

    }


    @Transactional
    public Status changeStatus(Status status) {
        List<String> errors = new ArrayList<>();
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Status statusChange = this.statusDao.findById(status.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Такой статус не найден id - " + status.getId()));
        if (!status.getTitle().isBlank()) {
            if (status.getTitle().length() > 3) {
                statusChange.setTitle(status.getTitle());
            } else {
                errors.add("Имя статуса должно быть больше чем 3 символа");
            }
        } else {
            errors.add("Имя статуса не может иметь пустое название");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        return statusChange;
    }
}
