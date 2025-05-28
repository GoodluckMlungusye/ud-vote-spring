package com.goodamcodes.repository;

import com.goodamcodes.model.Contestant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestantRepository extends JpaRepository<Contestant,Long> {
}
