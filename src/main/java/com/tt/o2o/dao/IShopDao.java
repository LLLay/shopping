package com.tt.o2o.dao;

import com.tt.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IShopDao {

    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);
    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
    /**
     * 根据id查询店铺
     */
    Shop queryByShopId(long shopId);

    /**
     * 分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，区域id，owner
     * rowIndex 从第几行开始取数据
     * pageSize 一页返回的条数
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shop,
                             @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    /**
     * 查询店铺数量
     */
    int queryShopCount(@Param("shopCondition") Shop shop);
}
