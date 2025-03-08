package com.hosiky.user.domain.dto.dto;

import lombok.Data;

@Data
public class CEndUserDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
}