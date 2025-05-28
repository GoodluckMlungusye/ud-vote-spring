package com.goodamcodes.repository;

import com.goodamcodes.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election,Long> {
}
