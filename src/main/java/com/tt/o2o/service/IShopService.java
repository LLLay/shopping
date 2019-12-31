package com.tt.o2o.service;

import com.tt.o2o.dto.ShopExecution;
import com.tt.o2o.entity.Shop;

import java.io.InputStream;

public interface IShopService {



    /**
     * 店铺列表 分页
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

    /**
     * 查询该用户下面的店铺信息
     *
     * @param employeeId
     *            employyeeId
     * @return List<Shop>
     * @throws Exception
     */
    ShopExecution getByEmployeeId(long employeeId) throws RuntimeException;

    /**
     * 根据ID查询店铺信息
     *
     * @param shopId
     *            shopId
     * @return Shop shop
     */
    Shop getByShopId(long shopId);

    /**
     * 创建商铺
     *
     * @param
     *            shop
     * @return ShopExecution shopExecution
     * @throws Exception
     */
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws RuntimeException;

    /**
     * 更新店铺信息（从店家角度）
     *
     * @param
     * @return
     * @throws RuntimeException
     */
    ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws RuntimeException;
}
