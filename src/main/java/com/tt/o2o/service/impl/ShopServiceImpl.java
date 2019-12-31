package com.tt.o2o.service.impl;

import com.tt.o2o.dao.IShopDao;
import com.tt.o2o.dto.ShopExecution;
import com.tt.o2o.entity.Shop;
import com.tt.o2o.enums.ShopStateEnum;
import com.tt.o2o.service.IShopService;
import com.tt.o2o.utlis.FileUtil;
import com.tt.o2o.utlis.PageCalculator;
import com.tt.o2o.utlis.imageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements IShopService {

    @Autowired
    private IShopDao iShopDao;


    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {

        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = iShopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count = iShopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if(shopList != null){
            se.setShopList(shopList);
            se.setCount(count);
        }else{
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    public ShopExecution getByEmployeeId(long employeeId) throws RuntimeException {
        return null;
    }

    @Override
    public Shop getByShopId(long shopId) {
        return iShopDao.queryByShopId(shopId);
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws RuntimeException {
        //空值判断
        if(shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
        }
        try {
            //给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺信息
            int effectedNum = iShopDao.insertShop(shop);
            //判断是否成功
            if(effectedNum <= 0){
                throw new RuntimeException("店铺创建失败");
            }else{
                if(shopImgInputStream != null){
                    //存储图片
                    try {
                        addShopImg(shop,shopImgInputStream,fileName);
                    }catch (Exception e){
                        throw new RuntimeException("addShopImg error"+e.getMessage());
                    }
                    //更新店铺的图片地址
                    effectedNum = iShopDao.updateShop(shop);
                    if (effectedNum <= 0){
                        throw new RuntimeException("更新店铺的图片地址失败");
                    }

                }
            }

        }catch (Exception e){
            throw new RuntimeException("addShop error:"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {
        //获取shop图片目录的相对值路径
        String dest = FileUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = imageUtil.generateThumbnail(shopImgInputStream,fileName,dest);
        shop.setShopImg(shopImgAddr);
    }

    /**
     * 更新店铺信息
     * @param shop
     * @param
     * @return
     * @throws RuntimeException
     */
    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream,String fileName) throws RuntimeException {

        if(shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOPID);
        }else {
            try {
                //1.判断是否需要处理图片
                if(shopImgInputStream != null){
                    Shop tempShop = iShopDao.queryByShopId(shop.getShopId());
                    if(tempShop.getShopImg() != null){
                        imageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop,shopImgInputStream,fileName);
                }
                //2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = iShopDao.updateShop(shop);
                if(effectedNum <= 0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else {
                    shop = iShopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                }
            }catch (Exception e){
                throw new RuntimeException("modifyShop error" + e.getMessage());
            }
        }
    }


}
