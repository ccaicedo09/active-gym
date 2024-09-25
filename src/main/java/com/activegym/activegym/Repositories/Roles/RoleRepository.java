package com.activegym.activegym.Repositories.Roles;

import com.activegym.activegym.Entities.Roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
