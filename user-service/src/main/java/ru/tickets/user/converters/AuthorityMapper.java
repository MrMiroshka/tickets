package ru.tickets.user.converters;

import ru.tickets.api.userservice.AuthorityDto;
import ru.tickets.user.entities.Authority;

import java.util.List;

public class AuthorityMapper {
    public static List<AuthorityDto> authorityDtoFromAuthority(List<Authority> authorities,String parent){
        return authorities.stream().map(authority -> authorityDtoFromAuthority(authority,parent)).toList();
    }

    public static List<Authority> authorityFromAuthorityDto(List<AuthorityDto> authorityDtos){
        return authorityDtos.stream().map(AuthorityMapper::authorityFromAuthorityDto).toList();
    }
    public static AuthorityDto authorityDtoFromAuthority(Authority authority){
        return authorityDtoFromAuthority(authority,null);
    }
    public static AuthorityDto authorityDtoFromAuthority(Authority authority,String parent){
        return  new AuthorityDto(authority.getId(),authority.getNameAuthority(),parent);
    }

    public static Authority authorityFromAuthorityDto(AuthorityDto authorityDto){
        return new Authority(authorityDto.getId(),authorityDto.getName());
    }
}
