package com.vue.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Income {
    private Integer incomeId;

    private String incomeName;

    private BigDecimal money;

    private String incomeway;

    private String label;
    
    private String notes;

    private Date datetime;

}