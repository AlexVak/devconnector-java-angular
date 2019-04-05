package com.alexvak.devconnectorrest.repository;

import com.alexvak.devconnectorrest.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {

    Optional<Status> findByName(String status);
}
