package com.tt.o2o.dto;

import com.tt.o2o.entity.Product;
import com.tt.o2o.enums.ProductStateEnum;

import java.util.List;

public class ProductExecution {

    //结果状态
    private int state;
    //状态标识
    private String  stateInfo;
    //操作的product(增删改商品的时候用)
    private Product product;
    //获取的商品列表(查询商品列表的时候用到)
    private List<Product> productList;

    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    //空的构造方法
    public ProductExecution(){

    }

    // 操作失败的有参构造器
    public ProductExecution(ProductStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }


    public ProductExecution(ProductStateEnum stateEnum, Product product) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.product = product;
    }
    // 操作成功使用的构造器
    public ProductExecution(ProductStateEnum stateEnum, List<Product> productList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productList = productList;
    }

    public void setCount(int count) {
    }
}
