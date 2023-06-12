package ru.tickets.settings.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.tickets.settings.data.Tracker;

@Repository
public interface TrackerDao extends JpaRepository<Tracker, Long>, JpaSpecificationExecutor<Tracker> {
    //Tracker findById(Long id);
}
