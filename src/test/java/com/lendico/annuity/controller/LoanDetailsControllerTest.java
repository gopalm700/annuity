package com.lendico.annuity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendico.annuity.domain.AnnuityPlan;
import com.lendico.annuity.domain.LoanInfo;
import com.lendico.annuity.domain.MonthlyPaymentPlan;
import com.lendico.annuity.service.PlanGeneratorService;
import java.math.BigDecimal;
import java.util.Date;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoanDetailsController.class)
public class LoanDetailsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlanGeneratorService fieldService;


    @Test
    public void shouldGetResponse() throws Exception {
        AnnuityPlan annuity = new AnnuityPlan();
        MonthlyPaymentPlan plan = new MonthlyPaymentPlan();
        plan.setDate(new Date());
        plan.setRemainingOutstandingPrincipal(new BigDecimal(100.04));
        plan.setPrincipal(new BigDecimal(50.99));
        plan.setInterest(new BigDecimal(10.98));
        plan.setInitialOutstandingPrincipal(new BigDecimal(162.01));
        plan.setBorrowerPaymentAmount(new BigDecimal(61.97));
        annuity.getPlans().add(plan);

        when(fieldService.getPlan(any(LoanInfo.class))).thenReturn(annuity);

        MvcResult result = mvc.perform(post("/generate-plan")
            .contentType("application/json")
            .content("{\"loanAmount\": \"5000\",\"nominalRate\": \"5.0\",\"duration\": 24,\n"
                + "\"startDate\": \"2018-01-01T00:00:01Z\"}"))
            .andExpect(status().isOk()).andReturn();

        assertThat(new ObjectMapper().writeValueAsString(annuity)).isEqualToIgnoringWhitespace(result.getResponse().getContentAsString());
        verify(fieldService, times(1)).getPlan(any(LoanInfo.class));
    }


    @Test
    public void shouldSendBadRequest() throws Exception {
        AnnuityPlan annuity = new AnnuityPlan();
        MonthlyPaymentPlan plan = new MonthlyPaymentPlan();
        plan.setDate(new Date());
        plan.setRemainingOutstandingPrincipal(new BigDecimal(100.04));
        plan.setPrincipal(new BigDecimal(50.99));
        plan.setInterest(new BigDecimal(10.98));
        plan.setInitialOutstandingPrincipal(new BigDecimal(162.01));
        plan.setBorrowerPaymentAmount(new BigDecimal(61.97));
        annuity.getPlans().add(plan);

        when(fieldService.getPlan(any(LoanInfo.class))).thenReturn(annuity);

        mvc.perform(post("/generate-plan")
            .contentType("application/json")
            .content("{\"badDATA\": \"5000\",\"nominalRate\": \"5.0\",\"duration\": 24,\n"
                + "\"startDate\": \"2018-01-01T00:00:01Z\"}"))
            .andExpect(status().isBadRequest());
    }
}
