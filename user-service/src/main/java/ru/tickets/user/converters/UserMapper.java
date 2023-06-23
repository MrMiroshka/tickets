package ru.tickets.user.converters;



import ru.gb.ticket.api.userservice.UserDto;
import ru.tickets.user.entities.User;

import java.util.List;

public class UserMapper {
    public static UserDto userDtoFromUser(User user){
        return new UserDto(user.getId(), user.getUsername(),"",user.getEmail(), RoleMapper.rolesDtoFromRoles(user.getRoles()));
    }
    public static User userFromUserDto(UserDto user){
        return new User(user.getId(),user.getName(),user.getPassword(),user.getEmail(),RoleMapper.rolesFromRolesDto(user.getRoles()));

    }
    public static List<UserDto> usersDtoFromUsers(List<User> users){
        return users.stream().map(UserMapper::userDtoFromUser).toList();
    }
}
