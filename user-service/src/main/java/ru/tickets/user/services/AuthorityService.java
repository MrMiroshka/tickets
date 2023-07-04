package ru.tickets.user.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.tickets.user.entities.Authority;
import ru.tickets.user.repositories.AuthorityRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorityService {
    private final AuthorityRepository authorityRepository;


    public Optional<Authority> findAuthorityById(Long id) {
        return authorityRepository.findById(id);
    }

    public Authority saveAuthority(Authority authority) {
        return authorityRepository.save(authority);
    }

    public void deleteAuthority(long id) {
        authorityRepository.deleteById(id);
    }

    public Page<Authority> findAuthoritys(String name, int page, int size) {
        Specification<Authority> specification = Specification.where(null);
        if (name != null) {
            specification = specification.and(nameLike(name));
        }

        return authorityRepository.findAll(specification, PageRequest.of(page - 1, size));
    }
    public static Specification<Authority> nameLike(String name){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nameroles"),String.format("%%%s%%",name)));
    }

}
