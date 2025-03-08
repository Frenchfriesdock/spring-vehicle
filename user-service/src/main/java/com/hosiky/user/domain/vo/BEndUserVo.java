package com.hosiky.user.domain.vo;

import lombok.Data;

@Data
public class BEndUserVo {

    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String companyName;

    private String businessLicense;

    private String address;

    private String token;
}
