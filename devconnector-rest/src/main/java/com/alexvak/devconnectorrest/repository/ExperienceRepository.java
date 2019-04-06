package com.alexvak.devconnectorrest.repository;

import com.alexvak.devconnectorrest.domain.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
