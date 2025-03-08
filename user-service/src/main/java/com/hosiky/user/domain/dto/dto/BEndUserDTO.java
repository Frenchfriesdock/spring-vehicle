package com.hosiky.user.domain.dto.dto;

import lombok.Data;

@Data
public class BEndUserDTO {
    private String username;
    private String password;
    private String email;
    private String companyName;  // B 端用户特有字段
    private String businessLicense;  // B 端用户特有字段
    private String phone;
    private String address;
}