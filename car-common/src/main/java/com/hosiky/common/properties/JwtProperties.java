package com.hosiky.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
/**
 * @ConfigurationProperties: 这是一个 Spring Boot 提供的注解，
 * 通常与 @EnableConfigurationProperties 配合使用，用于将配置文件中的属性值注入到 Spring 管理的 Bean 中。
 */
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    /**
     * 用户生成jwt令牌相关配置
     * # 1.令牌组成
     * - 1.标头(Header)
     * - 2.有效载荷(Payload)
     * - 3.签名(Signature)
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
}
