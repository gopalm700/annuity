package com.lendico.annuity.service;

import com.lendico.annuity.domain.AnnuityPlan;
import com.lendico.annuity.domain.LoanInfo;

public interface PlanGeneratorService {

    AnnuityPlan getPlan(LoanInfo info);

}
