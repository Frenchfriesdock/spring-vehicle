package com.hosiky.common.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RedisKeyBuilder {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String SPLII_TIME = ":";

    public String getPrefix(){
        return applicationName + SPLII_TIME;
    }
}
