package ru.tickets.core.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.circledevs.tasks.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
