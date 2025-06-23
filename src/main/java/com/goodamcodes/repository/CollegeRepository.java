package com.goodamcodes.repository;

import com.goodamcodes.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollegeRepository extends JpaRepository<College,Long> {
    Optional<College> findByNameAndElectionYear(String name, Integer electionYear);
}
