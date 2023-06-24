package ru.tickets.auth.converters;

import ru.tickets.api.auth.UserDto;
import ru.tickets.auth.entities.User;

import java.util.List;

public class UserMapper {
    public static UserDto userDtoFromUser(User user){
        return new UserDto(user.getId(), user.getUsername(), RoleMapper.rolesDtoFromRoles(user.getRoles()));
    }
    public static User userFromUserDto(UserDto userDto){
        return new User (userDto.getUsername(), RoleMapper.rolesFromRolesDto(userDto.getRoles()));
    }
    public static List<UserDto> usersDtoFromUsers(List<User> users){
        return users.stream().map(UserMapper::userDtoFromUser).toList();
    }
}
