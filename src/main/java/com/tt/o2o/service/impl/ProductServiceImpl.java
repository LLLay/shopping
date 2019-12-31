package com.tt.o2o.service.impl;

import com.tt.o2o.dao.IProductDao;
import com.tt.o2o.dao.IProductImgDao;
import com.tt.o2o.dto.ImageHolder;
import com.tt.o2o.dto.ProductExecution;
import com.tt.o2o.entity.Product;
import com.tt.o2o.entity.ProductImg;
import com.tt.o2o.enums.ProductStateEnum;
import com.tt.o2o.service.IProductService;
import com.tt.o2o.utlis.FileUtil;
import com.tt.o2o.utlis.PageCalculator;
import com.tt.o2o.utlis.imageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao iProductDao;
    @Autowired
    private IProductImgDao iProductImgDao;


    // 1. 处理缩略图，获取缩略图相对路径并赋值给product
    // 2. 往tb_product写入商品信息，获取productId
    // 3. 结合productId批量处理商品详情图
    // 4. 将商品的详情图列表批量插入tb_product_img中
    @Override
    // 事务管理
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageHolderList)
            throws RuntimeException {
        // 空值判断
        if (product !=null && product.getShop().getShopId() != null) {
            // 给商品设置默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            // 默认为上架状态
            product.setEnableStatus(1);
            // 若商品缩略图不为空则添加
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }

            try {
                // 创建商品信息
                int effectNum = iProductDao.insertProduct(product);
                if (effectNum <= 0) {
                    throw new RuntimeException("创建商品失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("创建商品失败" + e.getMessage());
            }
            // 若商品详情图不为空则添加
            if (imageHolderList != null && imageHolderList.size() > 0) {
                addProductImgList(product, imageHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            // 传入的参数为空
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product getProductById(long productId) {
        return iProductDao.queryProductByProductId(productId);
    }

    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws RuntimeException {
        //空值判断
        if (product !=null && product.getShop().getShopId() != null){
            //给商品设置默认属性
            product.setLastEditTime(new Date());
            //如果商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
            if(thumbnail != null){
                //先获取一遍原有信息，因为原来的信息里有原图片地址
                Product tempProduct = iProductDao.queryProductByProductId(product.getProductId());
                if(tempProduct.getImgAddr() != null){
                    imageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product,thumbnail);
            }

            //如果新存入的商品详情图，则将原来的删除，并添加新的
            if(productImgHolderList != null && productImgHolderList.size() > 0){
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgHolderList);
            }
            try {
                int effectedNum = iProductDao.updateProduct(product);
                if(effectedNum < 0){
                    throw new RuntimeException("更新失败");
                }else{
                    return new ProductExecution(ProductStateEnum.SUCCESS,product);
                }

            }catch (Exception e){
                 throw new RuntimeException("更新失败"+e.toString());
            }
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }

    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Product> productList = iProductDao.queryProductList(productCondition, rowIndex, pageSize);
        //查询商品总数
        int count = iProductDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }



    /**
     * 删除某个商品下的所有详情图
     * @param productId
     */
    private void deleteProductImgList(Long productId) {
        //根据productId获取原来的图片
        List<ProductImg> productImgList = iProductImgDao.queryProductImgList(productId);
        //删除
        for (ProductImg productImg : productImgList){
            imageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        iProductImgDao.deleteProductImgByProductId(productId);
    }

    /**
     * 批量添加图
     * @param product
     * @param imageHolderList
     */
    private void addProductImgList(Product product, List<ImageHolder> imageHolderList) {
        // 获取图片储存的路径，这里直接放在相应的店铺的文件夹底下
        String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        // 遍历图片一次去处理，并添加进productImg实体类
        for (ImageHolder imageHolder : imageHolderList) {
            String imgAddr = imageUtil.generateNormalImg(imageHolder.getImage(), imageHolder.getImageName(), dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }

        // 如果确实是有图片需要添加的 就执行批量添加
        if (productImgList.size() > 0) {
            try {
                int effectedNum = iProductImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new RuntimeException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("创建商品详情图片失败" + e.getMessage());

            }
        }
    }

    /**
     * 添加缩略图
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        // 获取根路径
        String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = imageUtil.generateThumbnail(thumbnail.getImage(), thumbnail.getImageName(), dest);
        product.setImgAddr(thumbnailAddr);
    }
}
