package ru.tickets.user.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.tickets.user.entities.Authority;

import java.util.Optional;

public interface AuthorityRepository extends CrudRepository<Authority,Long>, JpaSpecificationExecutor<Authority> {
        Optional<Authority> findByNameAuthority (String nameAuthority);

}
