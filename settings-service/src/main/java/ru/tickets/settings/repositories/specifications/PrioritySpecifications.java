package ru.tickets.settings.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.tickets.settings.data.Priority;

public class PrioritySpecifications {
    public static Specification<Priority> nameLike(String namePriority) {
        return (Specification<Priority>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get("title"), String.format("%%%s%%", namePriority));
    }
}
