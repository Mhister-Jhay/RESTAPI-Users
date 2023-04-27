package com.users.repository;

import com.users.domain.Roles;
import com.users.domain.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    boolean existsByUsernameOrEmail(String username, String email);

    Page<Users> findAllByRoles(Set<Roles> roles, Pageable pageable);
}
