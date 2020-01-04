package com.tt.o2o.service;

import com.tt.o2o.dto.LocalAuthExecution;
import com.tt.o2o.entity.LocalAuth;

/**
 * @Description: 本地用户信息服务层接口
 *
 */
public interface LocalAuthService {

    /**
     * 根据用户名、密码查询本地用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    LocalAuth getLocalAuthByUsernameAndPwd(String username, String password);

    /**
     * 根据用户名查询本地用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    LocalAuth getLocalAuthByUsername(String username);

    /**
     * 根据用户ID查询本地用户信息
     *
     * @param localAuthId 用户ID
     * @return 用户信息
     */
    LocalAuth queryLocalByLocalAuthId(long localAuthId);

    /**
     * 保存本地账号信息
     *
     * @param localAuth 本地用户
     * @return 操作返回信息
     */
    LocalAuthExecution saveLocalAuth(LocalAuth localAuth) throws RuntimeException;

    /**
     * 修改本地账号登录密码
     *
     * @param username    用户名
     * @param password    原密码
     * @param newPassword 新密码
     * @return 操作返回信息
     */
    LocalAuthExecution modifyLocalAuth(String username, String password, String newPassword)
            throws RuntimeException;
}