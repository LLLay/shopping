package com.tt.o2o.dao;

import com.tt.o2o.BaseTest;
import com.tt.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private IProductCategoryDao iProductCategoryDao;

    @Test
    public void testQueryByShopId() throws Exception{
        long shopId = 1;
        List<ProductCategory> productCategoryList = iProductCategoryDao.queryProductCategoryList(shopId);
        System.out.println(productCategoryList.size());
    }

    @Test
    public void testBatchInsertProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商铺类别1");
        productCategory.setPriority(20);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(6L);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商铺类别2");
        productCategory2.setPriority(10);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(6L);

        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);

        int effectedNum = iProductCategoryDao.batchInsertProductCategory(productCategoryList);

    }
}
