package com.gleyser.usermanagement.repository;

import com.gleyser.usermanagement.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    List<UserProfile> findAllByOrderByNameAsc();

    List<UserProfile> findByName(String name);
}
