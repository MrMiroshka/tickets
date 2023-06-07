package ru.tickets.core.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.circledevs.tasks.dto.ResourceNotFoundExceptions;
import ru.circledevs.tasks.dto.TaskDto;
import ru.circledevs.tasks.entities.Task;
import ru.circledevs.tasks.services.TaskService;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping()
    public List<TaskDto> getAllTasks(){
        log.info("Метод работает!");
        return taskService.getAllTasks().stream().map(task -> new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getComment(),
                task.getStatus(),
                task.getPriority()))
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public TaskDto findById(@PathVariable Long id){
        Task task = taskService.findById(id).orElseThrow(
                () -> new ResourceNotFoundExceptions("Продукт не найден, id:" + id));
        return new TaskDto(task.getId(),
                task.getTitle(),
                task.getComment(),
                task.getStatus(),
                task.getPriority());
    }

    @PostMapping("/create")
    public void create(@RequestBody TaskDto taskDto){
        taskService.createTask(taskDto);
    }
}
