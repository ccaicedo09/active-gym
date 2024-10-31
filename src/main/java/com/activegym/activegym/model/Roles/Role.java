package com.activegym.activegym.model.Roles;

import com.activegym.activegym.model.Users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Represents a user role in the system.
 * This entity is used to define different roles that can be assigned to users
 * for authorization and access control purposes.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@RequiredArgsConstructor
public class Role {

    /**
     * The unique identifier for the role.
     * This ID is automatically generated and serves as the primary key for the role entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the role.
     * This field must be unique and cannot be null.
     * There is a pre-set list of roles that cannot be modified: MIEMBRO, ENTRENADOR, ADMINISTRADOR, ASESOR and PERSONAL DE ASEO.
     */
    @Column(nullable = false, unique = true)
    private String roleName;

    /**
     * The set of users associated with this role.
     * This field is used for the many-to-many relationship between roles and users.
     * It is ignored during JSON serialization to prevent infinite recursion
     * when converting to JSON format.
     */
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;
}
