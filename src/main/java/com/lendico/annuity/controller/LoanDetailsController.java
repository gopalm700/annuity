package com.lendico.annuity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lendico.annuity.domain.AnnuityPlan;
import com.lendico.annuity.domain.LoanInfo;
import com.lendico.annuity.service.PlanGeneratorService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class LoanDetailsController {

    private final static Logger logger = LoggerFactory.getLogger(LoanDetailsController.class);

    @Autowired
    private PlanGeneratorService service;


    @PostMapping(value = "/generate-plan", produces = "application/json")
    public AnnuityPlan generatePlan(@Valid @RequestBody LoanInfo info) {
        logger.info("Generating plan");
        return service.getPlan(info.normalize());
    }
}
