package com.tt.o2o.service;

import com.tt.o2o.BaseTest;
import com.tt.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaServiceTest extends BaseTest {

    @Autowired
    private IAreaService iAreaService;

    @Test
    public void testGetAreaList(){
        List<Area> areaList = iAreaService.getAreaList();
        System.out.println(areaList);
    }


}
