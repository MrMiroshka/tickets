package ru.tickets.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.tickets.api.exceptions.ResourceNotFoundException;
import ru.tickets.api.userservice.AuthorityDto;
import ru.tickets.user.converters.AuthorityMapper;
import ru.tickets.user.services.AuthorityService;


@RestController
@RequestMapping("/authority")
public class AuthorityController {
    private AuthorityService authorityService;

    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping("/{id}")
    public AuthorityDto getAuthority(@PathVariable Long id){
        return AuthorityMapper.authorityDtoFromAuthority(authorityService.findAuthorityById(id).orElseThrow(
                () -> new ResourceNotFoundException("Право не найден id: " + id)));
    }
    @GetMapping("")
    public Page<AuthorityDto> users(@RequestParam(required = false) String name,
                                    @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return authorityService.findAuthoritys(name, page, size).map(AuthorityMapper::authorityDtoFromAuthority);
    }
}
