package ru.tickets.user.repositories;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.tickets.user.entities.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long>, JpaSpecificationExecutor<Role> {
        Optional<Role> findByNameRoles(String nameRole);

}
