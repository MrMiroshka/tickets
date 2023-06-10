package ru.tickets.auth.converters;

import ru.gb.ticket.api.RoleDto;
import ru.tickets.auth.entities.Role;

import java.util.List;

public class RoleMapper {
    public static List<RoleDto> rolesDtoFromRoles(List<Role> roles){
        return roles.stream().map(role -> new RoleDto(role.getId(),role.getTitle())).toList();
    }
}
