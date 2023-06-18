package ru.tickets.auth.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@Table(name = "roles")
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_roles")
    private Long id;
    private String nameRoles;

    public Role(String nameRoles) {
        this.nameRoles = nameRoles;
    }
}
