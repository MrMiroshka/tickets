package ru.tickets.api.userservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long id;
    private String name;
    private List<AuthorityDto> authorityDtos;


}
