package ru.tickets.user.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.tickets.user.entities.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long>, JpaSpecificationExecutor<User> {
        Optional<User> findByUsername(String username);

}
