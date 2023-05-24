package com.smallworld;

import com.smallworld.facade.facadeImpl.TransactionImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@SpringBootConfiguration
public class SmallworldApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmallworldApplication.class, args);
		try{
			TransactionImpl trans = new TransactionImpl();
			trans.getTotalTransAmount();
			trans.getTotalTransAmountSentBy("Tom Shelby");
			trans.getMaxTransAmount();
			trans.countUniqueCustomer();
			trans.CompliIssues("Tom Shelby");
			trans.getTransBeneficiaryByName();
			trans.getUnsolvedIssueById();
			trans.getAllSolvedIssue();
			trans.getTopThreeTransAmount();
			trans.getTopCustomerAmount();

		}catch(Exception e){e.printStackTrace();}
	}

}
