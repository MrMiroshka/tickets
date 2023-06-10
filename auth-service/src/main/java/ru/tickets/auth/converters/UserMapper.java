package ru.tickets.auth.converters;

import ru.gb.ticket.api.UserDto;
import ru.tickets.auth.entities.User;

import java.util.List;

public class UserMapper {
    public static UserDto userDtoFromUser(User user){
        return new UserDto(user.getId(), user.getName(), RoleMapper.rolesDtoFromRoles(user.getRoles()));
    }
    public static List<UserDto> usersDtoFromUsers(List<User> users){
        return users.stream().map(UserMapper::userDtoFromUser).toList();
    }
}
