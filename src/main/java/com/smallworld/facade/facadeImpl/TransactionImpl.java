package com.smallworld.facade.facadeImpl;

import com.google.gson.Gson;
import com.smallworld.dto.Transaction;
import com.smallworld.facade.facadeTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TransactionImpl implements facadeTransaction {

    private static final Logger logger = LogManager.getLogger(TransactionImpl.class);

    double Result = 0;
    long count = 0;

    @Autowired
    List<Transaction> trans;
    @Autowired
    List<Transaction> uniqueTrans;
    @Value("${files:transactions.json}")
    String fileNameJson;

    public void populateObjectFromJson() {

        // String pathFile = "transactions.json";
        Gson gson = new Gson();
        Set<Integer> isAdded = new HashSet<>();
        uniqueTrans = new ArrayList<>();
        logger.info("Starting populate Object From Json...! ");
        try {

            Transaction[] transData =  gson.fromJson(new FileReader(fileNameJson), Transaction[].class);
            trans = Arrays.asList(transData);
            trans.forEach(t-> {  if(!isAdded.contains(t.getMtn())){ uniqueTrans.add(t);
                isAdded.add(t.getMtn());}
            });
            logger.info("Ending populate Object From Json...! ");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    public double getTotalTransAmount() {
        logger.info("Starting Total Transaction Amount...! ");
        try {
            Result = uniqueTrans.stream().mapToDouble(Transaction::getAmount).sum();
            logger.info("Ending Total Transaction Amount...! ");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UnsupportedOperationException();
        }
        return Result;
    }

    public double getTotalTransAmountSentBy(String customerName) {
        logger.info("Starting Total Transaction Amount By Customer Name...!");
        try {
            Result = uniqueTrans.stream()
                    .filter(t -> t.getCustomerName().trim().equalsIgnoreCase(customerName.trim()))
                    .mapToDouble(Transaction::getAmount).sum();
            logger.info("Ending Total Transaction Amount By Customer Name...! ");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UnsupportedOperationException();
        }
        return Result;
    }

    public double getMaxTransAmount() {
        logger.info("Starting Max Transaction Amount...!");
        try {
            Result = trans.stream().mapToDouble(Transaction::getAmount).max().getAsDouble();
            logger.info("Ending Max Transaction Amount...!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UnsupportedOperationException();
        }
        return Result;
    }

    public long countUniqueCustomer() {
        logger.info("Starting Unique Customer Count ");
        try {
            List<String> senderNames = uniqueTrans.stream().map(n -> n.getBeneficiaryName()).distinct()
                    .collect(Collectors.toList());
            count = senderNames.size();
            logger.info("Ending Unique Customer Count ");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UnsupportedOperationException();
        }
        return count;
    }

    public boolean CompliIssues(String clientFullName) {
        logger.info("Statring Compli Issues ");
        try {
            count = trans.stream().filter(n -> !n.getIssueSolved()).count();
            logger.info("Compli Issues " + (count > 0));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UnsupportedOperationException();
        }
        return count > 0;
    }

    public Map<String, Object> getTransBeneficiaryByName() {
        logger.info("Statring Transactions by Beneficiary Name ");
        Map<String, Object> tot_Trans = new HashMap<>();
        try {
            trans.forEach(t -> tot_Trans.put(t.getBeneficiaryName(), t));
            logger.info("Ending Transactions by Beneficiary Name " + tot_Trans);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UnsupportedOperationException();
        }
        return tot_Trans;
    }

    public Set<Integer> getUnsolvedIssueById() {
        logger.info("Starting Un-solved Issue By Id...! ");
        Set<Integer> un_Solved = new HashSet<>();
        try {
            un_Solved = trans.stream().filter(n -> !n.getIssueSolved()).map(t -> t.getIssueId())
                    .collect(Collectors.toSet());
            logger.info("Ending Un-solved Issue By Id... " + (un_Solved));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UnsupportedOperationException();
        }
        return un_Solved;
    }

    public List<String> getAllSolvedIssue() {
        logger.info("Starting All Solved Issue...!" );
        List<String> solvedIssue = new ArrayList<>();
        try {
            solvedIssue = trans.stream().filter(n -> n.getIssueSolved())
                    .map(t -> t.getIssueMessage()).collect(Collectors.toList());
            logger.info("Ending All Solved Issue " + (solvedIssue));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UnsupportedOperationException();
        }
        return solvedIssue;
    }

    public List<Object> getTopThreeTransAmount() {
        logger.info("Starting Top Three Trans Amount...!" );
        List<Transaction> top3TransactionsByAmount = new ArrayList<>();
        try {
            top3TransactionsByAmount = uniqueTrans.stream()
                    .sorted(Comparator.comparing(Transaction::getAmount).reversed()).limit(3)
                    .collect(Collectors.toList());
            top3TransactionsByAmount.forEach(e->e.getAmount());
            logger.info("Ending Top Three Trans Amount...!" );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UnsupportedOperationException();
        }
        return Collections.singletonList(top3TransactionsByAmount);
    }


    public Optional<Object> getTopCustomerAmount() {
        logger.info("Starting Top Customer Trans Amount...!" );
        Optional topCustomer= null;
        try {
            Map<String, Double> senders = uniqueTrans.stream().collect(Collectors.groupingBy
                    (Transaction::getCustomerName, Collectors.summingDouble(Transaction::getAmount)));
            topCustomer= senders.entrySet().stream().max((e1,e2) -> e1.getValue()> e2.getValue()?1:-1);
            logger.info("Ending Top Customer Trans Amount...!" );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UnsupportedOperationException();
        }
        return topCustomer;
    }
}