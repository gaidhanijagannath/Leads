package com.MahindraFinance.InterviewProject;

import com.MahindraFinance.InterviewProject.Controller.LeadController;
import com.MahindraFinance.InterviewProject.Entity.Lead;
import com.MahindraFinance.InterviewProject.Exceptions.LeadAlreadyPresentExp;
import com.MahindraFinance.InterviewProject.Service.LeadService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static javax.swing.UIManager.get;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LeadController.class)
public class LeadControllerTest {

    @Autowired
    private MockMvc mockMvcTest;

    @MockBean
    private LeadService leadService;

    @Test
    public void SuccessGetLeadsByPhoneNumber() throws Exception {

        when(leadService.getAllLeadsByPhoneNumber("8877887788")).thenReturn(createMockLeads());

        mockMvcTest.perform((RequestBuilder) get("/leads/mobileNumber/8877887788"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.status", is("success")))
                .andExpect((ResultMatcher) jsonPath("$.data", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$.data[0].firstName", is("Vineet")))
                .andExpect((ResultMatcher) jsonPath("$.data[1].firstName", is("Vishal")));
    }

    @Test
    public void NoLeadFoundGetLeadByPhoneNumber() throws Exception {

        when(leadService.getAllLeadsByPhoneNumber("1234567890")).thenReturn(Collections.emptyList());

        mockMvcTest.perform((RequestBuilder) get("/leads/mobileNumber/1234567890"))
                .andExpect(status().isNotFound())
                .andExpect((ResultMatcher) jsonPath("$.status", is("error")))
                .andExpect((ResultMatcher) jsonPath("$.errorResponse.code", is("E10011")))
                .andExpect((ResultMatcher) jsonPath("$.errorResponse.messages[0]", is("No Lead found with the Phone Number 1234567890")));
    }

    @Test
    public void SuccessAddLead() throws Exception {
        when(leadService.addLead(any())).thenReturn("Created Leads Successfully");

        mockMvcTest.perform(post("/leads")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"leadId\":5678,\"firstName\":\"Vineet\",\"middleName\":\"\",\"lastName\":\"KV\",\"mobileNumber\":\"8877887788\",\"gender\":\"Male\",\"Date_of_Birth\":\"dd/mm/yyyy\",\"email\":\"v@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.status", is("success")))
                .andExpect((ResultMatcher) jsonPath("$.data", is("Created Leads Successfully")));
    }

    @Test
    public void LeadAlreadyExistsAddLead() throws Exception {

        when(leadService.addLead(any())).thenThrow(new LeadAlreadyPresentExp("Lead Already Exists"));

        mockMvcTest.perform(post("/leads")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"leadId\":5678,\"firstName\":\"Vineet\",\"middleName\":\"\",\"lastName\":\"KV\",\"mobileNumber\":\"8877887788\",\"gender\":\"Male\",\"dob\":\"dd/mm/yyyy\",\"email\":\"v@gmail.com\"}"))
                .andExpect(status().isBadRequest())
                .andExpect((ResultMatcher) jsonPath("$.status", is("error")))
                .andExpect((ResultMatcher) jsonPath("$.errorResponse.code", is("E10010")))
                .andExpect((ResultMatcher) jsonPath("$.errorResponse.messages", contains("Lead Already Exists")));
    }

    private List<Lead> createMockLeads() {
        Lead lead1 = new Lead();
        lead1.setId(5678);
        lead1.setFirstName("Vineet");
        lead1.setLastName("KV");
        lead1.setPhoneNumber("8877887788");
        lead1.setGender("Male");
        lead1.setDateOfBirth(new Date());
        lead1.setEmail("v@gmail.com");

        Lead lead2 = new Lead();
        lead2.setId(5679);
        lead2.setFirstName("Vishal");
        lead2.setLastName("Sonar");
        lead2.setPhoneNumber("8877887788");
        lead2.setGender("Male");
        lead2.setDateOfBirth(new Date());
        lead2.setEmail("vi@gmail.com");

        return Arrays.asList(lead1, lead2);
    }



}
