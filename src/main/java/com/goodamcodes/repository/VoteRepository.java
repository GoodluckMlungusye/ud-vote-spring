package com.goodamcodes.repository;

import com.goodamcodes.model.Category;
import com.goodamcodes.model.Election;
import com.goodamcodes.model.Student;
import com.goodamcodes.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    boolean existsByVoterAndCategoryAndElection(Student voter, Category category, Election election);
}
