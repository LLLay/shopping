package com.tt.o2o.service;

import com.tt.o2o.dto.ProductCategoryExecution;
import com.tt.o2o.dto.ProductExecution;
import com.tt.o2o.entity.ProductCategory;

import java.util.List;

public interface IProductCategoryService {

    /**
     * 查询商品类别列表
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);

    /**
     * 批量添加商品类别
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws RuntimeException;

    /**
     * 删除商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws RuntimeException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
            throws RuntimeException;



}
