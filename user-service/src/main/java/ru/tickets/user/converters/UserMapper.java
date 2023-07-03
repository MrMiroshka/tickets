package ru.tickets.user.converters;

import ru.tickets.api.userservice.AuthorityDto;
import ru.tickets.api.userservice.UserDto;
import ru.tickets.user.entities.User;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {
    public static UserDto userDtoFromUser(User user){
        Set<AuthorityDto> authorityDtoSet = new HashSet<>(AuthorityMapper.authorityDtoFromAuthority(user.getAuthority(),"user"));
        user.getRoles().forEach(role -> authorityDtoSet.addAll(AuthorityMapper.authorityDtoFromAuthority(role.getAuthority(),role.getNameRoles())));
        return new UserDto(user.getId(),
                user.getUsername(),
                "",
                user.getEmail(),
                RoleMapper.rolesDtoFromRoles(user.getRoles()),
                authorityDtoSet.stream().toList());
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