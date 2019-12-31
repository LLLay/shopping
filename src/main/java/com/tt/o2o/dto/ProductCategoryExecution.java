package com.tt.o2o.dto;

import com.tt.o2o.entity.ProductCategory;
import com.tt.o2o.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {

    private int state;
    // 状态标识
    private String  stateInfo;

    private List<ProductCategory> productCategoryList;

    // 无参构造器
    public ProductCategoryExecution() {
    }

    // 操作失败的有参构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 操作成功使用的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productCategoryList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
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

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
