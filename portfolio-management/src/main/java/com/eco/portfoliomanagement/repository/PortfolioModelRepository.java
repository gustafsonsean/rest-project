package com.eco.portfoliomanagement.repository;

import com.eco.portfoliomanagement.model.db.Advisor;
import com.eco.portfoliomanagement.model.db.PortfolioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PortfolioModelRepository extends PagingAndSortingRepository<PortfolioModel, Long> {
    Advisor findByGuid( String Guid );

    List<PortfolioModel> findByAdvisor( Advisor advisor);

    Page<PortfolioModel> findByAdvisor(Advisor advisor, Pageable pageable);

}
