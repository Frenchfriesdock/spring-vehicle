package com.hosiky.user.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hosiky.user.domain.Enum.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")

public class User {

    private Integer id;

    private String username;

    private String password;

    private String email;

    private UserType userType;

    private String companyName;

    private String businessLicense;

    private String phone;

    private String address;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
