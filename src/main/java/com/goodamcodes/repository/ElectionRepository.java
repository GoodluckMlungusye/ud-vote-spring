package com.goodamcodes.repository;

import com.goodamcodes.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElectionRepository extends JpaRepository<Election,Long> {
    Optional<Election> findByName(String name);
}
