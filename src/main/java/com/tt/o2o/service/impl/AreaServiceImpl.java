package com.tt.o2o.service.impl;

import com.tt.o2o.dao.IAreaDao;
import com.tt.o2o.entity.Area;
import com.tt.o2o.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements IAreaService {

    @Autowired
    private IAreaDao iAreaDao;

    @Override
    public List<Area> getAreaList() {
        return iAreaDao.queryArea();
    }
}
