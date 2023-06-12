package ru.tickets.user.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ru.tickets.user.entities.User;
import ru.tickets.user.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
}
