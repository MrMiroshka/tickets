package ru.tickets.user.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ru.tickets.api.exceptions.ResourceNotFoundException;
import ru.tickets.user.entities.Authority;
import ru.tickets.user.entities.Role;
import ru.tickets.user.entities.User;
import ru.tickets.user.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final RoleService roleService;
    private final AuthorityService authorityService;

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public Page<User> findUsers(String name,int page, int size) {
        Specification<User> specification = Specification.where(null);
        if (name != null) {
            specification = specification.and(nameLike(name));
        }

        return userRepository.findAll(specification, PageRequest.of(page - 1, size));
    }
    public static Specification<User> nameLike(String name){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"),String.format("%%%s%%",name)));
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Transactional
    public void addUserRole(Long userId, Long roleId) {
        User user = findUserById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь с id " + userId+ " не найден"));
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.getId().equals( roleId)) {
                return;
            }
        }
        roles.add(roleService.findRoleById(roleId).orElseThrow(() -> new ResourceNotFoundException("Роль с id " + roleId + " не найден")));
        saveUser(user);
    }
    @Transactional
    public void delUserRole(Long userId, Long roleId) {
        User user = findUserById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь с id " + userId+ " не найден"));
        List<Role> roles = user.getRoles();
        roles.remove(roleService.findRoleById(roleId).orElseThrow(() -> new ResourceNotFoundException("Роль с id " + roleId + " не найден")));
        saveUser(user);
    }
    @Transactional
    public void addUserAuthority(Long userId, Long authorityId) {
        User user = findUserById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь с id " + userId+ " не найден"));
        List<Authority> authorityList = user.getAuthority();
        for (Authority role : authorityList) {
            if (role.getId().equals( authorityId)) {
                return;
            }
        }
        authorityList.add(authorityService.findAuthorityById(authorityId).orElseThrow(() -> new ResourceNotFoundException("Право с id " + authorityId + " не найден")));
        saveUser(user);
    }
    @Transactional
    public void delUserAuthority(Long userId, Long authorityId) {
        User user = findUserById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь с id " + userId+ " не найден"));
        List<Authority> authorityList = user.getAuthority();
        authorityList.remove(authorityService.findAuthorityById(authorityId).orElseThrow(() -> new ResourceNotFoundException("Право с id " + authorityId + " не найден")));
        saveUser(user);
    }
}
