package com.eco.portfoliomanagement.service;

import com.eco.portfoliomanagement.model.db.Advisor;
import com.eco.portfoliomanagement.model.db.PortfolioModel;
import org.springframework.data.domain.Page;

import java.util.List;
// If this was an full implementation, all functions would be necessary.
public interface PortfolioModelService {

    PortfolioModel findById( Long id );

    PortfolioModel findByGuid( String guid );

    List<PortfolioModel> findByAdvisor(Advisor advisor);

    Page<PortfolioModel> findByAdvisorPaged( Advisor advisor, int page, int size);

    void savePortfolioModel( PortfolioModel portfolioModel );

    void updatePortfolioModel( PortfolioModel portfolioModel );

    void deletePortfolioModelById( Long id );

    List<PortfolioModel> findAllPortfolioModels();

    boolean isConnectionConfigurationExist( PortfolioModel portfolioModel );
}
