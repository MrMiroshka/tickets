package ru.circledevs.tasks.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.circledevs.tasks.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
