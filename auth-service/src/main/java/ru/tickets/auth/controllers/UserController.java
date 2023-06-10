package ru.tickets.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.ticket.api.ResourceNotFoundExceptions;
import ru.gb.ticket.api.UserDto;
import ru.tickets.auth.converters.UserMapper;
import ru.tickets.auth.services.UserService;


import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers(){
        return UserMapper.usersDtoFromUsers(userService.findAllUsers());
    }
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id){
        return UserMapper.userDtoFromUser(userService.findUserById(id).orElseThrow(
                () -> new ResourceNotFoundExceptions("Пользователь не найден id: " + id)));
    }

}
