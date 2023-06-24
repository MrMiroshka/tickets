package ru.tickets.settings.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.tickets.settings.data.Status;

public interface StatusDao extends JpaRepository<Status, Long>, JpaSpecificationExecutor<Status> {
}
