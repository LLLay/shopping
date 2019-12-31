package com.tt.o2o.dao;

import com.tt.o2o.entity.ProductImg;

import java.util.List;

public interface IProductImgDao {


    /**
    *
    * 批量添加图片
    * @param productImgList
    * @return
    */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 删除某商品的图片
     * @param productId
     */
    void deleteProductImgByProductId(Long productId);

    /**
     * 查询某商品的详情图
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(Long productId);
}
