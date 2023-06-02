package ru.circledevs.tasks.services;


import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.circledevs.tasks.dto.ResourceNotFoundExceptions;
import ru.circledevs.tasks.dto.RoleDto;
import ru.circledevs.tasks.dto.TaskDto;
import ru.circledevs.tasks.dto.UserDto;
import ru.circledevs.tasks.entities.Role;
import ru.circledevs.tasks.entities.User;
import ru.circledevs.tasks.repositories.UserRepository;


import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public Optional<User> findByUsername(String name) {
        return userRepository.findByName(name);

    }
    @Transactional
    public List<UserDetails> findAllUsersDetails(){
        List<User> allUsers = userRepository.findAll();
        List<UserDetails> usersDetails = new ArrayList<>();
        for(User user: allUsers){
            UserDetails users = new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
            usersDetails.add(users);
        }
        return usersDetails;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found",username)));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getTitle())).collect(Collectors.toList());
    }

    public List<TaskDto> findAllTaskByUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptions(String.format("User with id '%s' not found", id)));
        List<TaskDto> tasksDtoList = new ArrayList<>();

        return tasksDtoList;
    }
}
