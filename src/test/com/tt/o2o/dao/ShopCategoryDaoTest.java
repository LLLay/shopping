package com.tt.o2o.dao;

import com.tt.o2o.BaseTest;
import com.tt.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private  IShopCategoryDao iShopCategoryDao;

    @Test
    public void testQueryShopCategory(){
        List<ShopCategory> shopCategoryList = iShopCategoryDao.queryShopCategory(null);
        System.out.println(shopCategoryList);


    }
}
