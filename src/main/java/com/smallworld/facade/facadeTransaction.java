package com.smallworld.facade;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public interface facadeTransaction {

    double getTotalTransAmount();
    double getTotalTransAmountSentBy(String CustomerName);
    double getMaxTransAmount();
    long countUniqueCustomer();
    boolean CompliIssues(String CustomerName);
    Map<String, Object> getTransBeneficiaryByName();
    Set<Integer> getUnsolvedIssueById();
    List<String> getAllSolvedIssue();
    List<Object> getTopThreeTransAmount();
    Optional<Object> getTopCustomerAmount();
}
