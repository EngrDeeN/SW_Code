package com.smallworld.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction  {

    Integer mtn;
    Double amount;
    String customerName;
    Integer customerAge;
    String beneficiaryName;
    Integer beneficiaryAge;
    Integer issueId;
    Boolean issueSolved;
    String issueMessage;

}