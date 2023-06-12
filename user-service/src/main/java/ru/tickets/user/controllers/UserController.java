package ru.tickets.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.gb.storage.api.ResourceNotFoundExceptions;
import ru.gb.storage.api.UserDto;
import ru.tickets.user.converters.UserMapper;
import ru.tickets.user.services.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setBasketService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id){
        return UserMapper.userDtoFromUser(userService.findUserById(id).orElseThrow(
                () -> new ResourceNotFoundExceptions("Пользователь не найден id: " + id)));
    }

    @PostMapping("")
    public UserDto ProductSave(@RequestBody UserDto userDto) {
        return UserMapper.userDtoFromUser(userService.saveUser(UserMapper.userFromUserDto(userDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @GetMapping("")
    public Page<UserDto> users(@RequestParam(required = false) String name,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return userService.findUsers(name, page, size).map(UserMapper::userDtoFromUser);
    }
}
