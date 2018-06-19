package com.eco.portfoliomanagement.service;

import com.eco.portfoliomanagement.model.db.Advisor;
import com.eco.portfoliomanagement.model.db.PortfolioModel;
import com.eco.portfoliomanagement.repository.PortfolioModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service("portfolioModelService")
@Transactional
public class PortfolioModelServiceImpl implements PortfolioModelService {

    @Autowired
    PortfolioModelRepository portfolioModelRepository;
    @Override
    public PortfolioModel findById( Long id ) {
        return null;
    }

    @Override
    public PortfolioModel findByGuid( String guid ) {
        return null;
    }
    public List<PortfolioModel> findByAdvisor(Advisor advisor)
    {
        return portfolioModelRepository.findByAdvisor(advisor);
    }

    @Override
    public Page<PortfolioModel> findByAdvisorPaged( Advisor advisor, int page, int size ) {
        return portfolioModelRepository.findByAdvisor(advisor, new PageRequest(page,size));
    }

    @Override
    public void savePortfolioModel( PortfolioModel portfolioModel ) {
        portfolioModelRepository.save(portfolioModel);
    }
    // If this was an full implementation, we have the option of alternative 'update' logic over save logic.
    @Override
    public void updatePortfolioModel( PortfolioModel portfolioModel ) {
        savePortfolioModel(portfolioModel);
    }

    @Override
    public void deletePortfolioModelById( Long id ) {

    }

    @Override
    public List<PortfolioModel> findAllPortfolioModels() {
        return null;
    }

    @Override
    public boolean isConnectionConfigurationExist( PortfolioModel portfolioModel ) {
        return false;
    }
}
