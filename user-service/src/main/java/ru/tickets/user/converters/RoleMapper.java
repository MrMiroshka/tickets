package ru.tickets.user.converters;

import ru.tickets.api.userservice.RoleDto;
import ru.tickets.user.entities.Role;

import java.util.List;

public class RoleMapper {
    public static List<RoleDto> rolesDtoFromRoles(List<Role> roles){
        return roles.stream().map(role -> new RoleDto(role.getId(),role.getNameRoles())).toList();
    }

    public static List<Role> rolesFromRolesDto(List<RoleDto> roles){
        return roles.stream().map(role -> new Role(role.getId(),role.getName())).toList();
    }
}