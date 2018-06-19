package com.eco.portfoliomanagement.controller;

import com.eco.portfoliomanagement.App;
import com.eco.portfoliomanagement.model.db.Advisor;
import com.eco.portfoliomanagement.repository.AdvisorRepository;
import com.eco.portfoliomanagement.service.AdvisorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


    @RunWith(SpringRunner.class)

// we test only the SimpleController
    @WebMvcTest(AdvisorController.class)

    public class AdvisorControllerTest {

        // we inject the server side Spring MVC test support
        @Autowired
        private MockMvc mockMvc;

        // we mock the service, here we test only the controller
        // @MockBean is a Spring annotation that depends on mockito framework
        @MockBean
        private AdvisorService simpleServiceMocked;

        @Test
        public void testCreatingAdvisor() throws Exception {
            this.mockMvc.perform(post("/v1/advisor")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }

        @Test
        public void testCreatingModel() throws Exception {
            Advisor a = new Advisor();
            a.setAdvisorId("12345");

            // we set the result of the mocked service
            given(simpleServiceMocked.findByAdvisorId("12345"))
                    .willReturn(a);

            this.mockMvc.perform(put("/v1/advisor/12345/model").contentType("Application/JSON").content("{\n" +
                    "   \"name\":\"d9557296-5a16-4e30-a7aa-88216b89aaad\",\n" +
                    "   \"description\":\"example model with tech stocks\",\n" +
                    "   \"cashHoldingPercentage\":65,\n" +
                    "   \"driftPercentage\":5,\n" +
                    "   \"createdOn\":\"2017-03-01\",\n" +
                    "   \"modelType\":\"TAXABLE\",\n" +
                    "   \"rebalanceFrequency\":\"QUARTERLY\",\n" +
                    "   \"assetAllocations\":[\n" +
                    "      {\n" +
                    "         \"symbol\":\"AAPL\",\n" +
                    "         \"percentage\":30\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"symbol\":\"GOOG\",\n" +
                    "         \"percentage\":20\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"symbol\":\"IBM\",\n" +
                    "         \"percentage\":25\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"symbol\":\"FB\",\n" +
                    "         \"percentage\":25\n" +
                    "      }\n" +
                    "   ]\n" +
                    "}")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }
    }

