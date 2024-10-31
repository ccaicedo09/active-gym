package com.activegym.activegym.model.Users.auxiliary;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an EPS (Entidad Promotora de Salud) in the system.
 * This entity is used to define different health promotion entities
 * that can be associated with users for healthcare purposes.
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Getter
@Setter
@Entity
@Table(name = "eps")
public class Eps {
    /**
     * The unique identifier for the EPS.
     * This ID is automatically generated and serves as the primary key for the EPS entity.
     * Also, is used as the EPS foreign key in the {@link com.activegym.activegym.model.Users.User} entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the EPS.
     * This field must be unique across all EPS records and is used for identification purposes.
     */
    @Column(unique = true)
    private String epsName;
}
