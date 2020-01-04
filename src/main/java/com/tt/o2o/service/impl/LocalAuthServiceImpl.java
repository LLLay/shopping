package com.tt.o2o.service.impl;

import com.tt.o2o.dao.LocalAuthDao;
import com.tt.o2o.dto.LocalAuthExecution;
import com.tt.o2o.entity.LocalAuth;
import com.tt.o2o.enums.LocalAuthStateEnum;
import com.tt.o2o.enums.OperationStatusEnum;
import com.tt.o2o.service.LocalAuthService;
import com.tt.o2o.utlis.MD5;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description: 本地用户信息服务层接口实现
 *
 */
@Service
public class LocalAuthServiceImpl implements LocalAuthService {


    @Autowired
    private LocalAuthDao localAuthDao;


    @Override
    public LocalAuth getLocalAuthByUsernameAndPwd(String username, String password) {
        return localAuthDao.queryLocalByUsernameAndPwd(username, MD5.getMd5(password));
    }


    @Override
    public LocalAuth getLocalAuthByUsername(String username) {
        return localAuthDao.queryLocalByUsername(username);
    }


    @Override
    public LocalAuth queryLocalByLocalAuthId(long localAuthId) {
        return localAuthDao.queryLocalByLocalAuthId(localAuthId);
    }


    @Override
    @Transactional
    public LocalAuthExecution saveLocalAuth(LocalAuth localAuth) throws RuntimeException {
        // 空值判断
        if (localAuth == null || localAuth.getUsername() == null || localAuth.getPassword() == null) {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        // 保存数据
        localAuth.setCreateTime(new Date());
        localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
        try {
            int effectedNum = localAuthDao.insertLocalAuth(localAuth);
            if (effectedNum <= 0) {
                throw new RuntimeException("用户账号新增失败");
            } else {
                return new LocalAuthExecution(OperationStatusEnum.SUCCESS, localAuth);
            }
        } catch (Exception e) {
            throw new RuntimeException("insertLocalAuth error:" + e.getMessage());
        }
    }


    @Override
    public LocalAuthExecution modifyLocalAuth(String username, String password, String newPassword)
            throws RuntimeException {
        // 非空判断
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)
                && StringUtils.isNotBlank(newPassword)) {
            try {
                int effectedNum = localAuthDao.updateLocalAuth(username, MD5.getMd5(password), MD5.getMd5(newPassword),
                        new Date());
                if (effectedNum <= 0) {
                    return new LocalAuthExecution(LocalAuthStateEnum.ERROR_UPDATE);
                } else {
                    return new LocalAuthExecution(OperationStatusEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new RuntimeException("updateLocalAuth error:" + e.getMessage());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}