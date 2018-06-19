package com.eco.portfoliomanagement.repository;

import com.eco.portfoliomanagement.model.db.Advisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvisorRepository extends JpaRepository<Advisor, Long> {
    Advisor findByAdvisorId( String advisorId );
}
