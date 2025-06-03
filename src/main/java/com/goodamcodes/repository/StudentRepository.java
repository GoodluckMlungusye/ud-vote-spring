package com.goodamcodes.repository;

import com.goodamcodes.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByRegistrationNumber(String registrationNumber);
    List<Student> findAllByCollegeIdAndElectionYear(Long collegeId, int electionYear);

    @Query("""
        SELECT s FROM Student s
        WHERE s.college.id = :collegeId
          AND s.electionYear = :electionYear
          AND SIZE(s.votes) > 0
        """)
    List<Student> findVotedStudentsByCollegeAndYear(@Param("collegeId") Long collegeId, @Param("electionYear") int electionYear);

}
