package ru.tickets.user.converters;



import ru.tickets.api.userservice.UserDto;
import ru.tickets.user.entities.User;

import java.util.List;

public class UserMapper {
    public static UserDto userDtoFromUser(User user){
        return new UserDto(user.getId(),
                user.getUsername(),
                "",
                user.getEmail(),
                RoleMapper.rolesDtoFromRoles(user.getRoles()),
                AuthorityMapper.authorityDtoFromAuthority(user.getAuthority()));
    }
    public static User userFromUserDto(UserDto user){
        return new User(user.getId(),
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                RoleMapper.rolesFromRolesDto(user.getRoles()),
                AuthorityMapper.authorityFromAuthorityDto(user.getAuthorityDtos()));

    }
    public static UserDto userDtoFromUserSmall(User user){
        return new UserDto(
                user.getId(),
                user.getUsername(),
                "",
                user.getEmail(),
                null,
                null);
    }
}