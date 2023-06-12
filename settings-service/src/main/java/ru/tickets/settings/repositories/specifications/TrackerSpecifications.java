package ru.tickets.settings.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.tickets.settings.data.Tracker;

public class TrackerSpecifications {
    public static Specification<Tracker> nameLike(String nameTracker) {
        return (Specification<Tracker>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get("title"), String.format("%%%s%%", nameTracker));
    }

}
