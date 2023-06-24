package ru.tickets.settings.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.tickets.settings.data.Priority;

public interface PriorityDao extends JpaRepository<Priority, Long>, JpaSpecificationExecutor<Priority> {
}
