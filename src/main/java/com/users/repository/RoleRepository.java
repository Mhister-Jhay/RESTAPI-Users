package com.users.repository;

import com.users.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {
    Roles findByRole(String role);

    boolean existByRole(String role);
}
