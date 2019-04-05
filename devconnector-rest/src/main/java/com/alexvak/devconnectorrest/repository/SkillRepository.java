package com.alexvak.devconnectorrest.repository;

import com.alexvak.devconnectorrest.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
