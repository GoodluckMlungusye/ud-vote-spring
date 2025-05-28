package com.goodamcodes.repository;

import com.goodamcodes.model.College;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<College,Long> {
}
