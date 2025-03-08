package com.hosiky.user.domain.vo;

import lombok.Data;

@Data
public class CEndUserVo {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
}
