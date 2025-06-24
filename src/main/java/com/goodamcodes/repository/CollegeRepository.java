package com.goodamcodes.repository;

import com.goodamcodes.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollegeRepository extends JpaRepository<College,Long> {
    Optional<College> findByNameAndElectionYear(String name, Integer electionYear);
    @Query("SELECT DISTINCT c.electionYear FROM College c ORDER BY c.electionYear DESC")
    List<Integer> findDistinctElectionYearsDesc();
}
