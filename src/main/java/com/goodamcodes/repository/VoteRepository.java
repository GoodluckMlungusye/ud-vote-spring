package com.goodamcodes.repository;

import com.goodamcodes.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote,Long> {
}
