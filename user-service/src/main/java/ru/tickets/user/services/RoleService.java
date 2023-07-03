package ru.tickets.user.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.tickets.api.exceptions.ResourceNotFoundException;
import ru.tickets.user.entities.Authority;
import ru.tickets.user.entities.Role;
import ru.tickets.user.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final AuthorityService authorityService;

    public Optional<Role> findRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }

    public Page<Role> findRoles(String name, int page, int size) {
        Specification<Role> specification = Specification.where(null);
        if (name != null) {
            specification = specification.and(nameLike(name));
        }

        return roleRepository.findAll(specification, PageRequest.of(page - 1, size));
    }
    public static Specification<Role> nameLike(String name){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nameroles"),String.format("%%%s%%",name)));
    }

    public void addRoleAuthority(Long roleId, Long authorityId) {
        Role role = findRoleById(roleId).orElseThrow(() -> new ResourceNotFoundException("Роль с id " + roleId+ " не найден"));
        List<Authority> authorityList = role.getAuthority();
        for (Authority authority : authorityList) {
            if (authority.getId().equals( authorityId)) {
                return;
            }
        }
        authorityList.add(authorityService.findAuthorityById(authorityId).orElseThrow(() -> new ResourceNotFoundException("Роль с id " + authorityId + " не найден")));
        saveRole(role);
    }

    public void delRoleAuthority(Long roleId, Long authorityId) {
        Role role = findRoleById(roleId).orElseThrow(() -> new ResourceNotFoundException("Роль с id " + roleId+ " не найден"));
        List<Authority> authorityList = role.getAuthority();
        authorityList.remove(authorityService.findAuthorityById(authorityId).orElseThrow(() -> new ResourceNotFoundException("Право с id " + authorityId + " не найден")));
        saveRole(role);

    }
}
