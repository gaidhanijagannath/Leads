package com.MahindraFinance.InterviewProject.Response;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiFailureResp {

    private String statusMsg;
    private ErrorResponse errorResponse;

    public ApiFailureResp(String statusMsg, ErrorResponse errorResponse) {
        this.statusMsg = statusMsg;
        this.errorResponse = errorResponse;
    }
}
