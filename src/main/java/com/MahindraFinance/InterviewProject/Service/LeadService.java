package com.MahindraFinance.InterviewProject.Service;

import com.MahindraFinance.InterviewProject.Entity.Lead;
import com.MahindraFinance.InterviewProject.Exceptions.LeadAlreadyPresentExp;
import com.MahindraFinance.InterviewProject.Repositories.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    public String addLead(Lead lead) {

        if (leadRepository.checkForLead(lead.getId())) {

            throw new LeadAlreadyPresentExp("Lead existing already.");
        }

        leadRepository.save(lead);

        return "Created Leads Successfully.";
    }

    public List<Lead> getAllLeadsByPhoneNumber(String phoneNumber) {

        return leadRepository.findByPhoneNumber(phoneNumber);
    }

}
