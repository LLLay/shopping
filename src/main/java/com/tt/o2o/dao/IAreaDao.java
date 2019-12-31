package com.tt.o2o.dao;

import com.tt.o2o.entity.Area;

import java.util.List;

public interface IAreaDao {

    /**
     * 列出区域列表
     * @return areaList
     */
    List<Area> queryArea();
}
