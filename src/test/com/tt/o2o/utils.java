package com.tt.o2o;

import com.tt.o2o.utlis.DESUtil;

public class utils {
    // 测试

    public static void main(String[] args) {


        System.out.println(DESUtil.getEncryptString("root"));
        System.out.println(DESUtil.getDecryptString("123456"));
    }
}
