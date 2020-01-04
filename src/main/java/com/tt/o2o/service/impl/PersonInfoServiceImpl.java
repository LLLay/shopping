package com.tt.o2o.service.impl;

import com.tt.o2o.dao.PersonInfoDao;
import com.tt.o2o.dto.PersonInfoExecution;
import com.tt.o2o.entity.PersonInfo;
import com.tt.o2o.enums.OperationStatusEnum;
import com.tt.o2o.enums.PersonInfoStateEnum;
import com.tt.o2o.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description: 用户信息服务层接口实现类
 *
 * @author tyronchen
 * @date 2019年6月5日
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;

    /*
     * (non-Javadoc)
     *
     * @see com.tyron.o2o.service.PersonInfoService#queryInfoByUserId(long)
     */
    @Override
    public PersonInfo queryInfoByUserId(long userId) {
        return personInfoDao.queryInfoByUserId(userId);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.tyron.o2o.service.PersonInfoService#insertPersonInfo(com.tyron.o2o.entity
     * .PersonInfo)
     */
    @Override
    public PersonInfoExecution insertPersonInfo(PersonInfo user) {
        // 空值判断
        if (user == null || user.getLocalAuthId() == null || user.getName() == null) {
            return new PersonInfoExecution(PersonInfoStateEnum.NULL_PERSON_INFO);
        }
        // 设置默认信息
        user.setCreateTime(new Date());
        try {
            int effectedNum = personInfoDao.insertPersonInfo(user);
            if (effectedNum <= 0) {
                throw new RuntimeException("用户信息新增失败");
            } else {
                return new PersonInfoExecution(OperationStatusEnum.SUCCESS, user);
            }
        } catch (Exception e) {
            throw new RuntimeException("insertPersonInfo error:" + e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.tyron.o2o.service.PersonInfoService#updatePersonInfo(com.tyron.o2o.entity
     * .PersonInfo)
     */
    @Override
    public PersonInfoExecution updatePersonInfo(PersonInfo user) {
        // 空值判断
        if (user == null || user.getUserId() == null || user.getLocalAuthId() == null || user.getName() == null) {
            return new PersonInfoExecution(PersonInfoStateEnum.NULL_PERSON_INFO);
        }
        // 设置默认信息
        user.setLastEditTime(new Date());
        try {
            int effectedNum = personInfoDao.updatePersonInfo(user);
            if (effectedNum <= 0) {
                throw new RuntimeException("用户信息修改失败");
            } else {
                return new PersonInfoExecution(OperationStatusEnum.SUCCESS, user);
            }
        } catch (Exception e) {
            throw new RuntimeException("insertPersonInfo error:" + e.getMessage());
        }
    }

}