package com.eco.portfoliomanagement.service;

import com.eco.portfoliomanagement.model.db.Advisor;

import java.util.List;

// If this was an full implementation, all functions would be necessary.
public interface AdvisorService {

    Advisor findById( Long id );

    Advisor findByAdvisorId( String advisorId );

    void saveAdvisor( Advisor advisor );

    void updateAdvisor( Advisor advisor );

    void deleteAdvisorById( Long id );

    List<Advisor> findAllAdvisors();

    boolean isAdvisorExist( Advisor advisor );
}
