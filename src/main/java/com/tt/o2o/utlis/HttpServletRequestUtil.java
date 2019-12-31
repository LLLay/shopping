package com.tt.o2o.utlis;

import javax.servlet.http.HttpServletRequest;

/**
 * 解析request中的参数
 */
public class HttpServletRequestUtil {

    /**
     * 将request中的key值转化为相应的类型
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request,String key){

        try {
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }
    public static long getLong(HttpServletRequest request, String name) {

        try {
            return Long.valueOf(request.getParameter(name));
        } catch (Exception e) {
            return -1;
        }
    }
    public static Double getDouble(HttpServletRequest request, String name) {

        try {
            return Double.valueOf(request.getParameter(name));
        } catch (Exception e) {
            return -1d;
        }
    }

    public static Boolean getBoolean(HttpServletRequest request, String name) {

        try {
            return Boolean.valueOf(request.getParameter(name));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getString(HttpServletRequest request, String name) {
        try {
            String result = request.getParameter(name);
            if (result != null) {
                result = result.trim();
            }
            if ("".equals(result))
                result = null;
            return result;
        } catch (Exception e) {
            return null;
        }

    }
}
