package ru.circledevs.tasks.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.circledevs.tasks.dto.TaskDto;
import ru.circledevs.tasks.services.UserService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public List<TaskDto> findAllOrdersByUserId(@PathVariable Long id){
        return userService.findAllTaskByUserId(id);
    }
}
