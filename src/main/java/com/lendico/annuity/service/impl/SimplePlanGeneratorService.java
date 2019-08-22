package com.lendico.annuity.service.impl;

import com.lendico.annuity.domain.AnnuityPlan;
import com.lendico.annuity.domain.LoanInfo;
import com.lendico.annuity.domain.MonthlyPaymentPlan;
import com.lendico.annuity.service.PlanGeneratorService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class SimplePlanGeneratorService implements PlanGeneratorService {

    @Override public AnnuityPlan getPlan(LoanInfo info) {

        AnnuityPlan annuityPlan = new AnnuityPlan();

        BigDecimal annuity = getAnnuity(info.getNominalRate(), info.getLoanAmount(), info.getDuration());
        BigDecimal initialOutstandingPrinciple = info.getLoanAmount();

        for (int month = 1; month <= info.getDuration(); month++) {

            BigDecimal interest = getInterest(initialOutstandingPrinciple, info.getNominalRate());
            BigDecimal principal = getPrincipal(initialOutstandingPrinciple, interest, annuity);
            principal = initialOutstandingPrinciple.compareTo(principal) > 0 ? principal : initialOutstandingPrinciple;
            BigDecimal remainingOutstandingPrincipal = initialOutstandingPrinciple.subtract(principal);

            MonthlyPaymentPlan plan = new MonthlyPaymentPlan();
            plan.setPaymentDate(info.getStartDate(), month);
            plan.setBorrowerPaymentAmount(principal.add(interest));
            plan.setPrincipal(principal);
            plan.setInterest(interest);
            plan.setInitialOutstandingPrincipal(initialOutstandingPrinciple);
            plan.setRemainingOutstandingPrincipal(remainingOutstandingPrincipal);

            annuityPlan.getPlans().add(plan);

            initialOutstandingPrinciple = remainingOutstandingPrincipal;
        }

        return annuityPlan;
    }


    private BigDecimal getPrincipal(BigDecimal initialPrincipal, BigDecimal interest, BigDecimal annuity) {
        return (interest.compareTo(initialPrincipal) > 0) ? initialPrincipal : annuity.subtract(interest);
    }


    private BigDecimal getInterest(BigDecimal initalPricipal, double rate) {
        return new BigDecimal(rate * 30).multiply(initalPricipal).divide(BigDecimal.valueOf(360), RoundingMode.HALF_DOWN);
    }


    private BigDecimal getAnnuity(double rate, BigDecimal loan, int duration) {
        double ratePerMonth = rate / 12;
        return loan.multiply(new BigDecimal(ratePerMonth)).divide(new BigDecimal(1 - Math.pow(1 + ratePerMonth, -duration)), RoundingMode.HALF_DOWN);
    }
}
