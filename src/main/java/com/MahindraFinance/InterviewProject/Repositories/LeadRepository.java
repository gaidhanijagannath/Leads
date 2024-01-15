package com.MahindraFinance.InterviewProject.Repositories;

import com.MahindraFinance.InterviewProject.Entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {

    boolean checkForLead(Long leadID);

    List<Lead> findByPhoneNumber(String phoneNumber);
}
