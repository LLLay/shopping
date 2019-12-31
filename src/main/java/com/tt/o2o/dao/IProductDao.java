package com.tt.o2o.dao;

import com.tt.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProductDao {

    /**
     * 插入商品
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 根据ID查找商品
     * @param
     * @return
     */
    Product queryProductByProductId(long productId);

    /**
     * 更新商品信息
     */
    int updateProduct(Product product);
    /**
     * 删除指定商品下的所有详情图
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);

    /**
     * 查询商品列表，可输入的条件有：商品名，商品状态，店铺ID，商品类别
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition,
                                   @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);

    /**
     * 查询对应的商品总数
     * @param product
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * 删除商品类别之前 将商品列表Id设置类空
     * @param categoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);
}
