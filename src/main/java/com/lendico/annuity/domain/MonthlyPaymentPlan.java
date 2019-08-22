package com.lendico.annuity.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lendico.annuity.serializers.CustomBigDecimalSerializer;
import com.lendico.annuity.serializers.CustomDateSerializer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyPaymentPlan {

    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal borrowerPaymentAmount;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal initialOutstandingPrincipal;
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal principal;
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal interest;
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal remainingOutstandingPrincipal;


    public void setPaymentDate(Date startDate, int monthNum) {
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        startLocalDate = startLocalDate.plusMonths(monthNum);
        this.date = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
