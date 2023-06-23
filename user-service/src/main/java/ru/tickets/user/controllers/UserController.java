package ru.tickets.user.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import ru.gb.ticket.api.exceptions.ResourceNotFoundException;
import ru.gb.ticket.api.userservice.UserDto;
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
                () -> new ResourceNotFoundException("Пользователь не найден id: " + id)));
    }

    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username){
        return UserMapper.userDtoFromUser(userService.findUserByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Пользователь с именем " + username+ " не найден")));
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
