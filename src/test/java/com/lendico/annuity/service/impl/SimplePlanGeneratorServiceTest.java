package com.lendico.annuity.service.impl;

import com.lendico.annuity.domain.AnnuityPlan;
import com.lendico.annuity.domain.LoanInfo;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.Test;

public class SimplePlanGeneratorServiceTest {

    private SimplePlanGeneratorService service = new SimplePlanGeneratorService();


    @Test
    public void shouldGetPlan() throws ParseException {
        LoanInfo info = new LoanInfo();
        info.setDuration(3);
        info.setLoanAmount(new BigDecimal(200.00));
        info.setNominalRate(5.0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        info.setStartDate(format.parse("2019-08-01"));
        info.normalize();
        AnnuityPlan plan = service.getPlan(info);
        assertThat(plan.getPlans().size()).isEqualTo(3);
        assertThat(plan.getPlans().get(2).getRemainingOutstandingPrincipal().intValue()).isEqualTo(0);
    }


}
