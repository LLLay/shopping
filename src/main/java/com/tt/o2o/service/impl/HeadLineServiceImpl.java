package com.tt.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tt.o2o.cache.JedisUtil;
import com.tt.o2o.dao.IHeadLineDao;
import com.tt.o2o.entity.HeadLine;
import com.tt.o2o.service.CacheService;
import com.tt.o2o.service.IHeadLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeadLineServiceImpl implements IHeadLineService {


    private static final String HL_LIST_KEY = "";
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private IHeadLineDao headLineDao;


    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        List<HeadLine> headLineList = null;
        ObjectMapper mapper = new ObjectMapper();
        String key = HL_LIST_KEY;
        if (headLineCondition.getEnableStatus() != null) {
            key = key + "_" + headLineCondition.getEnableStatus();
        }
        // redis中不存在key，则设值
        if (!jedisKeys.exists(key)) {
            headLineList = headLineDao.selectHeadLineList(headLineCondition);
            // 将列表转为jsonString
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(headLineList);
                jedisStrings.set(key, jsonString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        } else {
            String jsonString = jedisStrings.get(key);
            // 将jsonString转为list
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try {
                headLineList = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        return headLineList;
    }
}
