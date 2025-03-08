package com.hosiky.common.utils;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SMSCacheKeyBuilder extends RedisKeyBuilder{

    public static final String SMS = "sms";

    public String buildSMSCacheKey(String phoneNumber){
//        标识
            return super.getPrefix() + SMS +  phoneNumber;
    }
}
