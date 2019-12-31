package com.tt.o2o.service.impl;

import com.tt.o2o.dao.IProductCategoryDao;
import com.tt.o2o.dao.IProductDao;
import com.tt.o2o.dto.ProductCategoryExecution;
import com.tt.o2o.entity.ProductCategory;
import com.tt.o2o.enums.ProductCategoryStateEnum;
import com.tt.o2o.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.dc.pr.PRError;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {


    @Autowired
    private IProductCategoryDao iProductCategoryDao;

    @Autowired
    private IProductDao iProductDao;

    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return iProductCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws RuntimeException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectedNum = iProductCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectedNum <= 0) {
                    throw new RuntimeException("商品类别创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS, productCategoryList);
                }
            } catch (Exception e) {
                throw new RuntimeException("batchAddProductCategory error" + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }


    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws RuntimeException {
        // TODO 将此商品类别下的商品的类别Id置为空
        try {
            int effectNum = iProductDao.updateProductCategoryToNull(productCategoryId);
            if (effectNum < 0) {
                throw new RuntimeException("店铺类别删除失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("店铺类别删除失败" + e.getMessage());
        }


        try {
            int effectedNum = iProductCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new RuntimeException("店铺类别删除失败");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new RuntimeException("deleteProductCategory error:" + e.getMessage());
        }
    }

}
