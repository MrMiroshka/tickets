package ru.tickets.settings.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.tickets.settings.data.Status;

public class StatusSpecifications {
    public static Specification<Status> nameLike(String nameStatus) {
        return (Specification<Status>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get("title"), String.format("%%%s%%", nameStatus));
    }
}
