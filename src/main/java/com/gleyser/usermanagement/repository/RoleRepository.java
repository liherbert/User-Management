package com.gleyser.usermanagement.repository;

import com.gleyser.usermanagement.entity.Role;
import com.gleyser.usermanagement.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByOrderByNameAsc();

    List<Role> findByName(String name);
}
