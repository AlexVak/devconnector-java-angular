package com.alexvak.devconnectorrest.repository;

import com.alexvak.devconnectorrest.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfileRepository extends JpaRepository<UserProfile, Long> {
}
