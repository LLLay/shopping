package com.tt.o2o.service;


import com.tt.o2o.dto.ImageHolder;
import com.tt.o2o.dto.ProductExecution;
import com.tt.o2o.entity.Product;


import java.util.List;

public interface IProductService {


    /**
     * 添加商品信息和图片处理
     * @return
     * @throws RuntimeException
     */
    ProductExecution addProduct(Product product,
                                ImageHolder thumbnail,
                                List<ImageHolder> productImgList
                                ) throws RuntimeException;

    /**
     * 根据商品Id查询
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     * 修改商品信息
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     * @throws RuntimeException
     */
    ProductExecution modifyProduct(Product product,ImageHolder thumbnail,
                                   List<ImageHolder> productImgHolderList
                                  )throws RuntimeException;

    /**
     * 查询商品列表并分页
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
}
