package com.tt.o2o.dao;

import com.tt.o2o.BaseTest;
import com.tt.o2o.entity.Product;
import com.tt.o2o.entity.ProductCategory;
import com.tt.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class ProductDaoTest extends BaseTest {

    @Autowired
    private IProductDao iProductDao;
    @Test
    public void testAInsertProduct() throws Exception {
        Shop shop1 = new Shop();
        shop1.setShopId(5L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(2L);
        Product product1 = new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试Desc1");
        product1.setImgAddr("test1");
        product1.setPriority(0);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);
        int effectedNum = iProductDao.insertProduct(product1);
    }

    @Test
    public void testDeleteProductImgByProduct() throws Exception{

        long productId = 19;
        int e = iProductDao.deleteProductImgByProductId(productId);
    }

    @Test
    public void testQueryProductByProductId() throws Exception{
        long productId = 1;
        Product product = iProductDao.queryProductByProductId(productId);

    }

    @Test
    public void testUpdateProduct() throws Exception{
        Product product = new Product();
        ProductCategory pc = new ProductCategory();
        Shop shop = new Shop();
        shop.setShopId(1L);
        pc.setProductCategoryId(1L);
        product.setProductId(1L);
        product.setCreateTime(new Date());
        product.setProductName("第一个");
        product.setShop(shop);
        product.setProductCategory(pc);

        iProductDao.updateProduct(product);
    }

    @Test
    public void TestQueryProductList() throws Exception{
        Product productCondition = new Product();

        productCondition.setProductName("111");
        List<Product> productList = iProductDao.queryProductList(productCondition,0,5);
        int count = iProductDao.queryProductCount(productCondition);
    }

    @Test
    public void testUpdateProductCategoryToNull(){
        //将productCategoryId为18下面的商品的商品类别置为空
        int effectedNum = iProductDao.updateProductCategoryToNull(18L);

    }
}
