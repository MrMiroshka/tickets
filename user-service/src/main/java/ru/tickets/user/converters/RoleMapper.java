package ru.tickets.user.converters;

import ru.tickets.api.userservice.RoleDto;
import ru.tickets.user.entities.Role;

import java.util.List;

public class RoleMapper {
    public static List<RoleDto> rolesDtoFromRoles(List<Role> roles){
        return roles.stream().map(role -> new RoleDto(role.getId(),role.getNameRoles(),null)).toList();
    }

    public static List<Role> rolesFromRolesDto(List<RoleDto> roles){
        return roles.stream().map(role -> new Role(role.getId(),role.getName(),null)).toList();
    }
    public static RoleDto roleDtoFromRole(Role role){
        return new RoleDto(role.getId(),role.getNameRoles(),AuthorityMapper.authorityDtoFromAuthority(role.getAuthority(),"role"));
    }
    public static Role roleFromRoleDto(RoleDto role){
        return new Role(role.getId(),role.getName(),AuthorityMapper.authorityFromAuthorityDto(role.getAuthorityDtos()));
    }
}