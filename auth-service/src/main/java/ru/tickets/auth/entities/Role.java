package ru.tickets.auth.entities;


import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_roles")
    private Long id;
    private String nameRoles;


}
