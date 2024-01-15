package com.MahindraFinance.InterviewProject.Controller;


import com.MahindraFinance.InterviewProject.Entity.Lead;
import com.MahindraFinance.InterviewProject.Exceptions.LeadAlreadyPresentExp;
import com.MahindraFinance.InterviewProject.Response.ApiFailureResp;
import com.MahindraFinance.InterviewProject.Response.ErrorResponse;
import com.MahindraFinance.InterviewProject.Response.SuccessResp;
import com.MahindraFinance.InterviewProject.Service.LeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leads")
public class LeadController {

    @Autowired
    private LeadService leadService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> createLead(@Valid @RequestBody Lead lead) {

        try {
            String response = leadService.addLead(lead);

            return new ResponseEntity<>(new SuccessResp("success", response), HttpStatus.OK);

        } catch (LeadAlreadyPresentExp exception) {

            return new ResponseEntity<>(
                    new ApiFailureResp(
                    "error",
                              new ErrorResponse(
                                      "E10010",
                                      Collections.singletonList(exception.getMessage()))),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    public ResponseEntity<?> getLeadsByPhoneNumber(@PathVariable String phoneNumber) {

        List<Lead> leads = leadService.getAllLeadsByPhoneNumber(phoneNumber);

        if (leads.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiFailureResp(
                            "error",
                            new ErrorResponse(
                                    "E10011",
                                    Collections.singletonList("No Lead found with the Mobile Number " + phoneNumber))),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new SuccessResp("success", leads.toString()), HttpStatus.OK);
    }



}
