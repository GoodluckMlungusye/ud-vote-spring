package com.goodamcodes.repository;

import com.goodamcodes.model.RegistrationYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationYearRepository extends JpaRepository<RegistrationYear,Long> {
}
