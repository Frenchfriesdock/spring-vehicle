package com.hosiky.user.controller;

import com.hosiky.common.domain.Result;
import com.hosiky.common.domain.ServiceResult;
import com.hosiky.common.utils.UserContext;
import com.hosiky.user.domain.dto.BEndUserDTO;
import com.hosiky.user.domain.dto.BEndUserLoginDTO;
import com.hosiky.user.domain.dto.CEndUserDTO;
import com.hosiky.user.domain.po.User;
import com.hosiky.user.domain.vo.BEndUserVo;
import com.hosiky.user.domain.vo.CEndUserVo;
import com.hosiky.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "车辆管理")
@RequestMapping("/user")
@Slf4j
@CrossOrigin

public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Operation(summary = "添加b端用户信息")
    @PostMapping("/b-end")
    public Result<User> addBEndUser(@RequestBody BEndUserDTO bEndUserDTO) {
        log.info("添加b端的数据类型:{}",bEndUserDTO.toString());
        ServiceResult<User> userResult = userService.addBUser(bEndUserDTO);
        return Result.fromServiceResult(userResult);
    }

    @Operation(summary = "添加c端用户信息")
    @PostMapping("/c-end")
    public Result<User> addCEndUser(@RequestBody CEndUserDTO cEndUserDTO) {
        log.info("添加c端用户:{}",cEndUserDTO.toString());
        ServiceResult<User> userResult = userService.addCUser(cEndUserDTO);
        return Result.fromServiceResult(userResult);
    }

    /**
     * 感觉这个是管理员端的作用，这个用不到
     * todo 这个感觉是管理员端的功能，管理员可以看到b端或者c端的数据
     * @return
     *
     */
    @Operation(summary = "查看用户信息")
    @GetMapping("/list")
    public Result<List<User>> gerAllUser(){
        log.info("查看所有用户的信息");
        System.out.println("输出的用户id的信息为：" + UserContext.getCurrentId());
        return Result.fromServiceResult(userService.findAllUser());
    }

    @Operation(summary = "修改b端用户信息")
    @PutMapping("/BEndUpdate")
    public Result<BEndUserVo> updateBEndUser(@RequestBody BEndUserDTO bEndUserDTO){
        userService.updateBUser(bEndUserDTO);
        return Result.fromServiceResult(userService.updateBUser(bEndUserDTO));
    }

    @Operation(summary = "修改c端用户信息")
    @PutMapping("/CEndUpdate")
    public Result<CEndUserVo> updateCEndUser(@RequestBody CEndUserDTO cEndUserDTO){
        userService.updateCUser(cEndUserDTO);
        return Result.fromServiceResult(userService.updateCUser(cEndUserDTO));
    }


    @Operation(summary = "B端用户登录")
    @PostMapping("/BEndUserDtoLogin")
    public Result<BEndUserVo> LoginBEndUser(@RequestBody BEndUserLoginDTO bEndUserLoginDTO){

        log.info("bEndUserDTO:{}",bEndUserLoginDTO.toString());
        userService.loginBEndUser(bEndUserLoginDTO);
        System.out.println(UserContext.getCurrentId());
        return Result.fromServiceResult(userService.loginBEndUser(bEndUserLoginDTO));
    }


    @GetMapping("/id")
    @Operation(summary = "测试数据类型")
    public Result<User> getUserDateById(){
        log.info("查看测试的数据");
        Long currentId = UserContext.getCurrentId();
        System.out.println(currentId);
        User user = userService.getById(currentId);
        return Result.success(user);
    }


    /**
     * todo
     * 阿里云和腾讯云要money和身份验证
     * 暂时就不实现
     * @param phone
     * @return
     */
    @PostMapping("sendSMS")
    public Result sendSMS(@RequestParam("phone") String phone){
        if (!StringUtils.hasText(phone)) {
            return Result.error(405,"请求参数异常");
        }
//        调用服务
        if(userService.sendLoginCode(phone)){
            return Result.success();
        } else {
            return Result.error(405,"发送请求失败");
        }
    }

    /**
     * redis使用的测试
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public Result<BEndUserVo> getUserId(@RequestParam Long userId){
        userService.getUserById(userId);
        return Result.fromServiceResult(userService.getUserById(userId));
    }

    @GetMapping("/email")
    public void sendCode(@RequestParam @NotBlank String email){
        System.out.println(UserContext.getCurrentId());
        userService.sendCode(email);
    }
}
