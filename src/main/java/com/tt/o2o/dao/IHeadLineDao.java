package com.tt.o2o.dao;

import com.tt.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IHeadLineDao {

    /**
     * 根据传入的条件查询
     * @param headLineCondition
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);

    /**
     * 根据传入的查询条件查询头条信息列表
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine> selectHeadLineList(@Param("headLineConditon") HeadLine headLineCondition);
}
