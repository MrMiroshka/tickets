package ru.tickets.user.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.tickets.api.exceptions.ResourceNotFoundException;
import ru.tickets.api.userservice.UserDto;
import ru.tickets.user.converters.UserMapper;
import ru.tickets.user.services.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
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
    public UserDto saveUser(@RequestBody UserDto userDto) {
        return UserMapper.userDtoFromUser(userService.saveUser(UserMapper.userFromUserDto(userDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @GetMapping("")
    public Page<UserDto> users(@RequestParam(required = false) String name,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return userService.findUsers(name, page, size).map(UserMapper::userDtoFromUserSmall);
    }
    @GetMapping("/addRole")
    public void addUserRole(@RequestParam Long userId,
                            @RequestParam Long roleId) {
        userService.addUserRole(userId,roleId);
    }

    @GetMapping("/delRole")
    public void delUserRole(@RequestParam Long userId,
                            @RequestParam Long roleId) {
        userService.delUserRole(userId,roleId);
    }

    @GetMapping("/addAuthority")
    public void addUserAuthority(@RequestParam Long userId,
                                 @RequestParam Long authorityId) {
        userService.addUserAuthority(userId,authorityId);
    }

    @GetMapping("/delAuthority")
    public void delUserAuthority(@RequestParam Long userId,
                                 @RequestParam Long authorityId) {
        userService.delUserAuthority(userId,authorityId);
    }

}
