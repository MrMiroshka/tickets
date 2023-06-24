package ru.tickets.api.userservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String password;
    private String email;
    private List<RoleDto> roles;
    private List<AuthorityDto> authorityDtos;

}
