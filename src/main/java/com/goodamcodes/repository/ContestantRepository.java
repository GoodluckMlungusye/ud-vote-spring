package com.goodamcodes.repository;

import com.goodamcodes.model.Category;
import com.goodamcodes.model.Contestant;
import com.goodamcodes.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContestantRepository extends JpaRepository<Contestant,Long> {
    Optional<Contestant> findByStudent(Student student);
    boolean existsByIdAndCategory(Long id, Category category);

}
