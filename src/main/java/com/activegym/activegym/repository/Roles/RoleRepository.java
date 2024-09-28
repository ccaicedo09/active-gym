package com.activegym.activegym.repository.Roles;

import com.activegym.activegym.model.Roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
