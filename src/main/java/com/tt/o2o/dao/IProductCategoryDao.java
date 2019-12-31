package com.tt.o2o.dao;

import com.tt.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProductCategoryDao {

    /**
     * 通过shop id 查询店铺商品类别
     */
    List<ProductCategory> queryProductCategoryList(long shopId);


    /**
     * 批量添加商品类别
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除商品类别
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);

}
