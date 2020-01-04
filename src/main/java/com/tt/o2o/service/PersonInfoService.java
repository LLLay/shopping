package com.tt.o2o.service;

import com.tt.o2o.dto.PersonInfoExecution;
import com.tt.o2o.entity.PersonInfo;

/**
 * @Description: 用户信息服务层接口
 *
 */
public interface PersonInfoService {

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    PersonInfo queryInfoByUserId(long userId);

    /**
     * 添加平台账号
     *
     * @param user 用户信息
     * @return 操作返回信息
     */
    PersonInfoExecution insertPersonInfo(PersonInfo user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 操作返回信息
     */
    PersonInfoExecution updatePersonInfo(PersonInfo user);
}