package com.hosiky.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.hosiky.common.context.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;


public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求头中的用户信息
        String userInfo = request.getHeader("userInfo");
        // 2.判断是否为空
        if (StrUtil.isNotBlank(userInfo)) {
            // 不为空，保存到ThreadLocal

            /**
             * todo 这个地方UserContext储存了，但是在controller查不到
             */
            UserContext.setCurrentId(Long.valueOf(userInfo));
            System.out.println(UserContext.getCurrentId());
        }
        // 3.放行
            return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除用户
        UserContext.removeCurrentId();

    }
}