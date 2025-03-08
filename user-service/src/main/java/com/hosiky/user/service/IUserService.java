package com.hosiky.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hosiky.common.domain.ServiceResult;
import com.hosiky.user.domain.dto.BEndUserDTO;
import com.hosiky.user.domain.dto.BEndUserLoginDTO;
import com.hosiky.user.domain.dto.CEndUserDTO;
import com.hosiky.user.domain.po.User;
import com.hosiky.user.domain.vo.BEndUserVo;
import com.hosiky.user.domain.vo.CEndUserVo;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface IUserService extends IService<User> {

    ServiceResult<User> addBUser(BEndUserDTO bEndUserDTO);

    ServiceResult<User> addCUser(CEndUserDTO cEndUserDTO);

    ServiceResult<List<User>> findAllUser();

    ServiceResult<BEndUserVo> updateBUser(BEndUserDTO bEndUserDTO);

    ServiceResult<CEndUserVo> updateCUser(CEndUserDTO cEndUserDTO);

    ServiceResult<BEndUserVo> loginBEndUser(BEndUserLoginDTO bEndUserLoginDTO);

    /**
     * 发送短信验证
     * @param phone
     * @return
     */
    boolean sendLoginCode(String phone);

    ServiceResult<BEndUserVo> getUserById(Long userId);

    void sendCode(@NotBlank String email);
}
