package com.tt.o2o.service;

import com.tt.o2o.entity.HeadLine;

import java.util.List;

public interface IHeadLineService {


    /**
     * 根据条件查询头条列表
     * @param headLineCondition
     * @return
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition);
}
