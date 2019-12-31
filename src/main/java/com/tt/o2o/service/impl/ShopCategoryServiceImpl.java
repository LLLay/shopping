package com.tt.o2o.service.impl;

import com.tt.o2o.dao.IShopCategoryDao;
import com.tt.o2o.entity.ShopCategory;
import com.tt.o2o.service.IShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements IShopCategoryService {
    @Autowired
    private IShopCategoryDao iShopCategoryDao;

    /**
     * 根据查询条件返回商品类别
     * @param shopCategoryCondition
     * @return
     */
    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {

        return iShopCategoryDao.queryShopCategory(shopCategoryCondition);
    }
}
