package com.tt.o2o.dao;

import com.tt.o2o.BaseTest;
import com.tt.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaDaoTest extends BaseTest {

    @Autowired
    private IAreaDao iAreaDao;

    @Test
    public void testQueryArea(){
        List<Area> areaList = iAreaDao.queryArea();
        System.out.println(areaList);
    }

}
