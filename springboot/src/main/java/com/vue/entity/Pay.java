package com.vue.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Pay {
    private Integer payId;

    private String payName;

    private BigDecimal money;

    private String payway;

    private String label1;
    
    private String label2;
    
    private String notes;

    private Date datetime;

    private String toperson;

    private String place;

}