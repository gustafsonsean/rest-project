package com.eco.portfoliomanagement.service;

import com.eco.portfoliomanagement.model.db.Advisor;
import com.eco.portfoliomanagement.repository.AdvisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("advisorService")
@Transactional
public class AdvisorServiceImpl implements AdvisorService {

    @Autowired
    AdvisorRepository advisorRepository;

    @Override
    public Advisor findById( Long id ) {
        return advisorRepository.findOne(id);
    }

    @Override
    public Advisor findByAdvisorId( String advisorId ) {
        return advisorRepository.findByAdvisorId(advisorId);
    }

    @Override
    public void saveAdvisor( Advisor advisor ) {
        advisorRepository.save(advisor);

    }
    // If this was an full implementation, we have the option of alternative 'update' logic over save logic.
    @Override
    public void updateAdvisor( Advisor advisor ) {
        saveAdvisor(advisor);
    }

    @Override
    public void deleteAdvisorById( Long id ) {
        advisorRepository.delete(id);
    }

    @Override
    public List<Advisor> findAllAdvisors() {
        return advisorRepository.findAll();
    }

    @Override
    public boolean isAdvisorExist( Advisor advisor ) {
        return false;
    }
}
