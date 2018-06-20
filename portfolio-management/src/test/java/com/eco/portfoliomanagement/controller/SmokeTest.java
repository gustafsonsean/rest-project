package com.eco.portfoliomanagement.controller;

import com.eco.portfoliomanagement.repository.AdvisorRepository;
import com.eco.portfoliomanagement.repository.PortfolioModelRepository;
import com.eco.portfoliomanagement.service.AdvisorService;
import com.eco.portfoliomanagement.service.PortfolioModelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private AdvisorController advisorController;

    @Autowired
    AdvisorService advisorService;

    @Autowired
    PortfolioModelService portfolioModelService;

    @Autowired
    AdvisorRepository advisorRepository;

    @Autowired
    PortfolioModelRepository portfolioModelRepository;

    @Test
    public void contexLoads() throws Exception {
        assertThat(advisorController).isNotNull();
        assertThat(advisorService).isNotNull();
        assertThat(portfolioModelService).isNotNull();
        assertThat(advisorRepository).isNotNull();
        assertThat(portfolioModelRepository).isNotNull();
    }
}