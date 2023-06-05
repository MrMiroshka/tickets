package ru.circledevs.tasks.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.circledevs.tasks.dto.TaskDto;
import ru.circledevs.tasks.entities.Task;
import ru.circledevs.tasks.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public void createTask(TaskDto taskDto) {
        Task task = new Task(
                taskDto.getTitle(),
                taskDto.getComment(),
                "Запланирована",
                taskDto.getPriority());
        taskRepository.save(task);
    }
}
