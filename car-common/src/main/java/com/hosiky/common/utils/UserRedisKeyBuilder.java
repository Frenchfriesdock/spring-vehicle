package com.hosiky.common.utils;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRedisKeyBuilder {

    /**
     * 定义一个公共的前缀
     */
    private static final String USER_PREFIX = "user:";

    public String buildUserKey(Long userId) {

        return USER_PREFIX + userId;
    }
}
