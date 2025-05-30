package com.goodamcodes.repository;

import com.goodamcodes.model.Contestant;
import com.goodamcodes.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContestantRepository extends JpaRepository<Contestant,Long> {
    Optional<Contestant> findByStudent(Student student);
}
