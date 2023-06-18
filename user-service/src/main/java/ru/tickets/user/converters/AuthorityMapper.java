package ru.tickets.user.converters;

import ru.gb.ticket.api.userservice.AuthorityDto;
import ru.tickets.user.entities.Authority;

import java.util.List;

public class AuthorityMapper {
    public static List<AuthorityDto> authorityDtoFromAuthority(List<Authority> authorities){
        return authorities.stream().map(authority -> new AuthorityDto(authority.getId(),authority.getNameAuthority())).toList();
    }

    public static List<Authority> authorityFromAuthorityDto(List<AuthorityDto> authorityDtos){
        return authorityDtos.stream().map(authorityDto -> new Authority(authorityDto.getId(),authorityDto.getName())).toList();
    }
}
