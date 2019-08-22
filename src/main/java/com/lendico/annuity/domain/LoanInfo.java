package com.lendico.annuity.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class LoanInfo {
    @NotNull
    private BigDecimal loanAmount;
    @Positive
    private Double nominalRate;
    @Positive
    private Integer duration;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startDate;


    public LoanInfo normalize() {
        this.nominalRate = nominalRate / 100;
        return this;
    }
}
