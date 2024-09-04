package com.vue.entity;

import lombok.Data;

@Data
public class Member {
    private Integer memberId;

    private String memberName;

    private String tel;

    private String email;

    private String pwd;

    private String cardId;

    private String sex;

    private String career;

    private String role;
}
