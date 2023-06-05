package ru.circledevs.tasks.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.circledevs.tasks.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByName(String name);

}
