package com.activegym.activegym.model.Users;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents an access log in the system. This entity is used to store the access logs of the users in the system,
 * including the date and time of the access, the user that accessed the system and if the access was successful or not.
 * @since v1.3
 * @author Carlos Esteban Castro Caicedo
 */
@Entity
@Table(name = "access_logs")
@Getter
@Setter
@RequiredArgsConstructor
public class AccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_date_time", nullable = false)
    private LocalDateTime accessDateTime;

    /**
     * And user access attempt is stored either the result was successful or not.
     */
    @JsonProperty("wasSuccessful")
    @Column(name = "success", nullable = false)
    private Boolean success;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;
}
