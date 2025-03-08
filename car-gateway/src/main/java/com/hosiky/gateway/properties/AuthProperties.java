package com.hosiky.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "auth")

public class AuthProperties {

//    需要认证的方式
    private List<String> includePaths;

//    白名单
    private List<String> excludePaths;
}
