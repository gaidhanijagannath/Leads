package com.MahindraFinance.InterviewProject.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class SuccessResp {

    private String statusMsg;
    private String response;

}
