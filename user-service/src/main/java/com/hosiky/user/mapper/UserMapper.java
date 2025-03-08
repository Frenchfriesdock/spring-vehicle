package com.hosiky.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hosiky.user.domain.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where phone = #{phone} and password = #{password}")
    User getUser(String phone, String password);
}
