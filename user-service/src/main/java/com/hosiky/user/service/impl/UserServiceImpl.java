package com.hosiky.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hosiky.common.client.MailClient;
import com.hosiky.common.domain.ServiceResult;
import com.hosiky.common.properties.JwtProperties;
import com.hosiky.common.utils.JwtUtils;
import com.hosiky.common.utils.SMSCacheKeyBuilder;
import com.hosiky.common.utils.UserRedisKeyBuilder;
import com.hosiky.user.domain.Enum.UserType;
import com.hosiky.user.domain.dto.BEndUserDTO;
import com.hosiky.user.domain.dto.BEndUserLoginDTO;
import com.hosiky.user.domain.dto.CEndUserDTO;
import com.hosiky.user.domain.po.User;
import com.hosiky.user.domain.vo.BEndUserVo;
import com.hosiky.user.domain.vo.CEndUserVo;
import com.hosiky.user.mapper.UserMapper;
import com.hosiky.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserRedisKeyBuilder userRedisKeyBuilder;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private SMSCacheKeyBuilder smsCacheKeyBuilder;

    @Autowired
    private MailClient mailClient;


    @Override
    public ServiceResult<User> addBUser(BEndUserDTO bEndUserDTO) {
        if(bEndUserDTO != null) {
            User user = new User();
            BeanUtils.copyProperties(bEndUserDTO, user);
//            密码进行md5进行加密处理
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            user.setUserType(UserType.B);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            this.save(user);
            return ServiceResult.success(user);
        } else {
            return ServiceResult.error(500,"添加b端用户失败");
        }
    }

    @Override
    public ServiceResult<User> addCUser(CEndUserDTO cEndUserDTO) {
        if(cEndUserDTO != null) {
            User user = new User();
            BeanUtils.copyProperties(cEndUserDTO, user);
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            user.setUserType(UserType.C);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            this.save(user);
            return ServiceResult.success(user);
        } else {
            return ServiceResult.error(500,"添加c端用户失败");
        }
    }

    @Override
    public ServiceResult<List<User>> findAllUser() {
        List<User> users = this.list();
        return ServiceResult.success(users);
    }

    @Override
    public ServiceResult<BEndUserVo> updateBUser(BEndUserDTO bEndUserDTO) {
        if(bEndUserDTO != null) {
            User user = new User();
            BeanUtils.copyProperties(bEndUserDTO, user);
            user.setUpdateTime(LocalDateTime.now());
            this.updateById(user);
            BEndUserVo bEndUserVo = new BEndUserVo();
            BeanUtils.copyProperties(bEndUserDTO, bEndUserVo);
            return ServiceResult.success(bEndUserVo);
        } else {
            return ServiceResult.error(500,"修改b端用户失败");
        }
    }

    @Override
    public ServiceResult<CEndUserVo> updateCUser(CEndUserDTO cEndUserDTO) {
        if (cEndUserDTO != null) {
            User user = new User();
            BeanUtils.copyProperties(cEndUserDTO, user);
            user.setUpdateTime(LocalDateTime.now());
            this.updateById(user);
            CEndUserVo cEndUserVo = new CEndUserVo();
            BeanUtils.copyProperties(cEndUserDTO, cEndUserVo);
            return ServiceResult.success(cEndUserVo);
        } else {
            return ServiceResult.error(500,"修改c端用户失败");
        }
    }

    @Override
    public ServiceResult<BEndUserVo> loginBEndUser(BEndUserLoginDTO bEndUserLoginDTO) {
        if (bEndUserLoginDTO != null) {
            String phone = bEndUserLoginDTO.getPhone();
            String password = DigestUtils.md5DigestAsHex(bEndUserLoginDTO.getPassword().getBytes());

            User user = userMapper.getUser(phone,password);

            if(user != null) {
                BEndUserVo bEndUserVo = new BEndUserVo();
                BeanUtils.copyProperties(user,bEndUserVo);

//              登录成功后，生成jwt令牌
                Map<String,Object> claims = new HashMap<>();
                claims.put("id",user.getId());
                claims.put("username",user.getUsername());

                String token = JwtUtils.createJwt(
                        jwtProperties.getUserSecretKey(),
                        jwtProperties.getUserTtl(),
                        claims
                );
                bEndUserVo.setToken(token);
                return ServiceResult.success(bEndUserVo);
            } else {
                return ServiceResult.error(500, "该用户不存在");
            }
        } else {
            return ServiceResult.error(500,"不正确");
        }
    }

    @Override
    public boolean sendLoginCode(String phone) {

//        参数校验
        if(!StringUtils.hasText(phone)){
            return false;
        }
//        生成一个和手机号相关联的key(redis)
        String smsCacheKey = smsCacheKeyBuilder.buildSMSCacheKey(phone);
//        去redis查询是否发送过验证码
        Object smsRecord = redisTemplate.opsForValue().get(smsCacheKey);
//        如果没有，就发送验证码，并将验证码保存到redis中
        if(redisTemplate.hasKey(smsCacheKey)) {
            log.info("手机号 {} 申请短信过于频繁",phone);
            return false;
        } else {
            int smsCode = new Random().nextInt(100, 999);
            redisTemplate.opsForValue().set(smsCacheKey,smsCode,60, TimeUnit.SECONDS);
            /**
             * todo 发送验证短信
             */
            boolean sendSMS = true;

        }
//        如果有记录，就表示已经发送了验证码，返回false
        return false;
    }

    @Override
    public ServiceResult<BEndUserVo> getUserById(Long userId) {
        // 参数校验
        if (userId == null) {
            return ServiceResult.error(500,"用户ID不能为空");
        }

        String userKey = userRedisKeyBuilder.buildUserKey(userId);
        BEndUserVo bEndUserVo = null;
        bEndUserVo = (BEndUserVo) redisTemplate.opsForValue().get(userKey);


        // 缓存命中且非空值占位对象
        if (bEndUserVo != null) {
            return ServiceResult.success(bEndUserVo);
        }

        // 缓存未命中，查询数据库
        User user = userMapper.selectById(userId);
        if (user != null) {
            // 数据库不存在时，缓存空值防止缓存穿透（设置较短过期时间）
            bEndUserVo = new BEndUserVo();
            BeanUtils.copyProperties(user,bEndUserVo);
            redisTemplate.opsForValue().set(userKey, bEndUserVo, 5, TimeUnit.MINUTES);
            return ServiceResult.success(bEndUserVo);
        }

//        如果数据库里面没有用户信息
//        这个可以通过把uesrId设置成一个不合理的值，存在redis里面
        return null;
    }

    @Override
    public void sendCode(String email) {
        String code = RandomUtil.randomString(6);
        redisTemplate.opsForValue().set(email,code,5, TimeUnit.MINUTES);
        mailClient.sendMail(email,code);
    }
}
