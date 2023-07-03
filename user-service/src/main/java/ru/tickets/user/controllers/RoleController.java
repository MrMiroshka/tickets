package ru.tickets.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.tickets.api.exceptions.ResourceNotFoundException;
import ru.tickets.api.userservice.RoleDto;
import ru.tickets.user.converters.RoleMapper;
import ru.tickets.user.services.RoleService;


@RestController
@RequestMapping("/role")
public class RoleController {
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public RoleDto getRole(@PathVariable Long id){
        return RoleMapper.roleDtoFromRole(roleService.findRoleById(id).orElseThrow(
                () -> new ResourceNotFoundException("Пользователь не найден id: " + id)));
    }

    @PostMapping("")
    public RoleDto ProductSave(@RequestBody RoleDto roleDto) {
        return RoleMapper.roleDtoFromRole(roleService.saveRole(RoleMapper.roleFromRoleDto(roleDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        roleService.deleteRole(id);
    }

    @GetMapping("")
    public Page<RoleDto> users(@RequestParam(required = false) String name,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return roleService.findRoles(name, page, size).map(RoleMapper::roleDtoFromRole);
    }

    @GetMapping("/addAuthority")
    public void addUserAuthority(@RequestParam Long roleId,
                                 @RequestParam Long authorityId) {
        roleService.addRoleAuthority(roleId,authorityId);
    }

    @GetMapping("/delAuthority")
    public void delUserAuthority(@RequestParam Long roleId,
                                 @RequestParam Long authorityId) {
        roleService.delRoleAuthority(roleId,authorityId);
    }
}
