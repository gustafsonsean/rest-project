package com.eco.portfoliomanagement.controller;

import com.eco.portfoliomanagement.model.db.Advisor;
import com.eco.portfoliomanagement.model.db.PortfolioModel;
import com.eco.portfoliomanagement.model.db.PortfolioModelAssetAllocation;
import com.eco.portfoliomanagement.model.web.AssetAllocation;
import com.eco.portfoliomanagement.model.web.PortfolioModelWeb;
import com.eco.portfoliomanagement.service.AdvisorService;
import com.eco.portfoliomanagement.service.PortfolioModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasValue;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.hamcrest.Matchers.is;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)

    @WebMvcTest(AdvisorController.class)

    public class AdvisorControllerTest {
        @Autowired
        private MockMvc mvc;

        @MockBean
        private AdvisorService advisorService;
        @MockBean
        private PortfolioModelService portfolioModelService;
        Advisor advisor;

        PortfolioModelWeb portfolioModelWeb;
        PortfolioModel portfolioModel;

        @Autowired
        AdvisorController advisorController;
        @Before
        public void setUp() throws Exception {
            advisor = new Advisor();
            advisor.setAdvisorId("TestID");
            advisor.setId(1L);

            portfolioModelWeb = new PortfolioModelWeb();
            portfolioModelWeb.setDescription("Example Description");
            portfolioModelWeb.setRebalanceFrequency(PortfolioModelWeb.RebalanceFrequency.ANNUAL);
            portfolioModelWeb.setName("Test1");
            portfolioModelWeb.setDriftPercentage(15);
            portfolioModelWeb.setCreatedOn("2017-03-01");
            portfolioModelWeb.setCashHoldingPercentage(10);
            portfolioModelWeb.setModelType(PortfolioModelWeb.ModelType.QUALIFIED);

            AssetAllocation assetAllocation = new AssetAllocation();
            assetAllocation.setPercentage(Double.valueOf(50));
            assetAllocation.setSymbol("GOOG");
            AssetAllocation assetAllocation2 = new AssetAllocation();
            assetAllocation2.setPercentage(Double.valueOf(50));
            assetAllocation2.setSymbol("AAPL");
            List<AssetAllocation> assetAllocationList = Arrays.asList(assetAllocation, assetAllocation2);

            portfolioModelWeb.setAssetAllocations(assetAllocationList);


            portfolioModel = new PortfolioModel();
            portfolioModel.setDescription("Example Description");
            portfolioModel.setRebalanceFrequency(PortfolioModel.RebalanceFrequency.ANNUAL);
            portfolioModel.setName("Test2");
            portfolioModel.setDriftPercentage(15);
            portfolioModel.setCreatedOn("2017-03-01");
            portfolioModel.setCashHoldingPercentage(10);
            portfolioModel.setModelType(PortfolioModel.ModelType.QUALIFIED);

            PortfolioModelAssetAllocation portfolioModelAssetAllocation = new PortfolioModelAssetAllocation();
            portfolioModelAssetAllocation.setPercentage(Double.valueOf(50));
            portfolioModelAssetAllocation.setSymbol("GOOG");
            PortfolioModelAssetAllocation portfolioModelAssetAllocation2 = new PortfolioModelAssetAllocation();
            portfolioModelAssetAllocation2.setPercentage(Double.valueOf(50));
            portfolioModelAssetAllocation2.setSymbol("AAPL");
            List<PortfolioModelAssetAllocation> assetAllocationListTest2 = Arrays.asList(portfolioModelAssetAllocation, portfolioModelAssetAllocation2);

            portfolioModel.setPortfolioModelAssetAllocations(assetAllocationListTest2);
        }

        @After
        public void tearDown() throws Exception {
        }

        //Testing paged contents
        @Test
        public void getAllModelsForAdvisorId() throws Exception {
            Page<PortfolioModel> page = mock(Page.class);
            List<PortfolioModel> portfolioModelList = Arrays.asList(portfolioModel);
            when(page.getContent()).thenReturn(portfolioModelList);
            when(page.getTotalElements()).thenReturn(1L);
            when(page.getTotalPages()).thenReturn(1);
            when(page.getNumber()).thenReturn(1);
            when(page.getSize()).thenReturn(1);
            given(advisorService.findByAdvisorId("TestID"))
                    .willReturn(advisor);
            given(portfolioModelService.findByAdvisorPaged(advisor,0,1)).willReturn(page);
            this.mvc.perform(get("/v1/advisor/TestID/model?pageSize=1&pageNumber=0").contentType("Application/JSON")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.pageNumber", is(1)))
                    .andExpect(jsonPath("$.pageSize", is(1)))
                    .andExpect(jsonPath("$.numberOfPages", is(1)))
                    .andExpect(jsonPath("$.totalNumberOfElements", is(1)))
                    .andExpect(jsonPath("$.page[0].name", is("Test2")));

        }

        @Test
        public void testCreatingModel() throws Exception {
            given(advisorService.findByAdvisorId("TestID"))
                    .willReturn(advisor);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String requestJson=ow.writeValueAsString(portfolioModelWeb );


            this.mvc.perform(put("/v1/advisor/TestID/model").contentType("Application/JSON").content(requestJson)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
            .andExpect(jsonPath("$.advisorId", is(advisor.getAdvisorId())));
        }

    @Test
    public void TestModelUpdating() throws Exception {
        given(advisorService.findByAdvisorId("TestID"))
                .willReturn(advisor);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(portfolioModelWeb );


        this.mvc.perform(put("/v1/advisor/TestID/model").contentType("Application/JSON").content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(portfolioModelWeb.getName())));


        PortfolioModelWeb portfolioModelWebUpdate = new PortfolioModelWeb();
        portfolioModelWebUpdate.setDescription("Changed Example");
        portfolioModelWebUpdate.setRebalanceFrequency(PortfolioModelWeb.RebalanceFrequency.ANNUAL);
        portfolioModelWebUpdate.setName("Test1");
        portfolioModelWebUpdate.setDriftPercentage(15);
        portfolioModelWebUpdate.setCreatedOn("2017-03-01");
        portfolioModelWebUpdate.setCashHoldingPercentage(10);
        portfolioModelWebUpdate.setModelType(PortfolioModelWeb.ModelType.QUALIFIED);

        AssetAllocation assetAllocation = new AssetAllocation();
        assetAllocation.setPercentage(Double.valueOf(50));
        assetAllocation.setSymbol("GOOG");
        AssetAllocation assetAllocation2 = new AssetAllocation();
        assetAllocation2.setPercentage(Double.valueOf(50));
        assetAllocation2.setSymbol("AAPL");
        List<AssetAllocation> assetAllocationList = Arrays.asList(assetAllocation, assetAllocation2);

        portfolioModelWebUpdate.setAssetAllocations(assetAllocationList);

        String requestJsonAlternate =ow.writeValueAsString(portfolioModelWebUpdate );

        //shows description updates but name remains the old name
        this.mvc.perform(put("/v1/advisor/TestID/model").contentType("Application/JSON").content(requestJsonAlternate)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(portfolioModelWeb.getName())))
                .andExpect(jsonPath("$.description", is(portfolioModelWebUpdate.getDescription())));

    }

    @Test
    public void TestModelCreation_WhenAdvisorIsNotFound() throws Exception {
        given(advisorService.findByAdvisorId("TestID"))
                .willReturn(advisor);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(portfolioModelWeb );


        this.mvc.perform(put("/v1/advisor/FakeAdvisor/model").contentType("Application/JSON").content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", is("advisor.not.found")));
    }

    @Test
    public void TestModelCreation_WhenAllocationPercentageIsOff() throws Exception {

        PortfolioModelWeb portfolioModelWeb = new PortfolioModelWeb();
        portfolioModelWeb.setDescription("Example Description");
        portfolioModelWeb.setRebalanceFrequency(PortfolioModelWeb.RebalanceFrequency.ANNUAL);
        portfolioModelWeb.setName("Test1");
        portfolioModelWeb.setDriftPercentage(15);
        portfolioModelWeb.setCreatedOn("2017-03-01");
        portfolioModelWeb.setCashHoldingPercentage(10);
        portfolioModelWeb.setModelType(PortfolioModelWeb.ModelType.QUALIFIED);

        AssetAllocation assetAllocation = new AssetAllocation();
        assetAllocation.setPercentage(Double.valueOf(56));
        assetAllocation.setSymbol("GOOG");
        AssetAllocation assetAllocation2 = new AssetAllocation();
        assetAllocation2.setPercentage(Double.valueOf(50));
        assetAllocation2.setSymbol("AAPL");
        List<AssetAllocation> assetAllocationList = Arrays.asList(assetAllocation, assetAllocation2);

        portfolioModelWeb.setAssetAllocations(assetAllocationList);
        given(advisorService.findByAdvisorId("TestID"))
                .willReturn(advisor);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(portfolioModelWeb );


        this.mvc.perform(put("/v1/advisor/TestID/model").contentType("Application/JSON").content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is("allocation.percentage.total.invalid")));
    }

    @Test
    public void TestModelCreation_WhenAllocationPercentageIsCloseDecimal() throws Exception {

        PortfolioModelWeb portfolioModelWeb = new PortfolioModelWeb();
        portfolioModelWeb.setDescription("Example Description");
        portfolioModelWeb.setRebalanceFrequency(PortfolioModelWeb.RebalanceFrequency.ANNUAL);
        portfolioModelWeb.setName("Test1");
        portfolioModelWeb.setDriftPercentage(15);
        portfolioModelWeb.setCreatedOn("2017-03-01");
        portfolioModelWeb.setCashHoldingPercentage(10);
        portfolioModelWeb.setModelType(PortfolioModelWeb.ModelType.QUALIFIED);

        AssetAllocation assetAllocation = new AssetAllocation();
        assetAllocation.setPercentage(Double.valueOf(50.1));
        assetAllocation.setSymbol("GOOG");
        AssetAllocation assetAllocation2 = new AssetAllocation();
        assetAllocation2.setPercentage(Double.valueOf(49.91));
        assetAllocation2.setSymbol("AAPL");
        List<AssetAllocation> assetAllocationList = Arrays.asList(assetAllocation, assetAllocation2);

        portfolioModelWeb.setAssetAllocations(assetAllocationList);
        given(advisorService.findByAdvisorId("TestID"))
                .willReturn(advisor);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(portfolioModelWeb );


        this.mvc.perform(put("/v1/advisor/TestID/model").contentType("Application/JSON").content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is("allocation.percentage.total.invalid")));
    }

    @Test
    public void TestModelCreation_WhenAllocationPercentageIsMissing() throws Exception {

        PortfolioModelWeb portfolioModelWeb = new PortfolioModelWeb();
        portfolioModelWeb.setDescription("Example Description");
        portfolioModelWeb.setRebalanceFrequency(PortfolioModelWeb.RebalanceFrequency.ANNUAL);
        portfolioModelWeb.setName("Test1");
        portfolioModelWeb.setDriftPercentage(15);
        portfolioModelWeb.setCreatedOn("2015-03-03");
        portfolioModelWeb.setCashHoldingPercentage(10);
        portfolioModelWeb.setModelType(PortfolioModelWeb.ModelType.QUALIFIED);

        AssetAllocation assetAllocation = new AssetAllocation();
        assetAllocation.setPercentage(Double.valueOf(56));
        assetAllocation.setSymbol("GOOG");
        AssetAllocation assetAllocation2 = new AssetAllocation();
        assetAllocation2.setPercentage(Double.valueOf(50));
        assetAllocation2.setSymbol("AAPL");
        List<AssetAllocation> assetAllocationList = Arrays.asList(assetAllocation, assetAllocation2);
        portfolioModelWeb.setAssetAllocations(assetAllocationList);
        given(advisorService.findByAdvisorId("TestID"))
                .willReturn(advisor);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(portfolioModelWeb );


        this.mvc.perform(put("/v1/advisor/TestID/model").contentType("Application/JSON").content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is("allocation.percentage.total.invalid")));
    }

    @Test
    public void TestModelCreation_WhenDateIsBad() throws Exception {

        PortfolioModelWeb portfolioModelWeb = new PortfolioModelWeb();
        portfolioModelWeb.setDescription("Example Description");
        portfolioModelWeb.setRebalanceFrequency(PortfolioModelWeb.RebalanceFrequency.ANNUAL);
        portfolioModelWeb.setName("Test1");
        portfolioModelWeb.setDriftPercentage(15);
        portfolioModelWeb.setCreatedOn("2017-03-55");
        portfolioModelWeb.setCashHoldingPercentage(10);
        portfolioModelWeb.setModelType(PortfolioModelWeb.ModelType.QUALIFIED);
        AssetAllocation assetAllocation = new AssetAllocation();
        assetAllocation.setPercentage(Double.valueOf(50));
        assetAllocation.setSymbol("GOOG");
        AssetAllocation assetAllocation2 = new AssetAllocation();
        assetAllocation2.setPercentage(Double.valueOf(50));
        assetAllocation2.setSymbol("AAPL");
        List<AssetAllocation> assetAllocationList = Arrays.asList(assetAllocation, assetAllocation2);
        portfolioModelWeb.setAssetAllocations(assetAllocationList);
        given(advisorService.findByAdvisorId("TestID"))
                .willReturn(advisor);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(portfolioModelWeb );


        this.mvc.perform(put("/v1/advisor/TestID/model").contentType("Application/JSON").content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is("date.created.on.invalid")));
    }
}

