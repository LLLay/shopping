package com.tt.o2o.dao;

import com.tt.o2o.BaseTest;
import com.tt.o2o.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HeadLineDaoTest extends BaseTest {

    @Autowired
    private IHeadLineDao iHeadLineDao;

    @Test
    public void TestHeadLineDao(){
        HeadLine headLine = new HeadLine();
        List<HeadLine> headLineList = iHeadLineDao.queryHeadLine(headLine);
    }
}
