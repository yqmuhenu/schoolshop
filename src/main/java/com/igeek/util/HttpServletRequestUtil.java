package com.igeek.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    /**
     * 获取整型参数
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request,String key){
        try {
            return Integer.parseInt(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * 获取长整型参数
     * @param request
     * @param key
     * @return
     */
    public static long getLong(HttpServletRequest request,String key){
        try {
            return Long.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * 获取double类型参数
     * @param request
     * @param name
     * @return
     */
    public static Double getDouble(HttpServletRequest request, String name) {
        try {
            return Double.valueOf(request.getParameter(name));
        } catch (Exception e) {
            return -1d;
        }
    }

    /**
     * 获取boolean类型参数
     * @param request
     * @param name
     * @return
     */
    public static Boolean getBoolean(HttpServletRequest request, String name) {

        try {
            return Boolean.valueOf(request.getParameter(name));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取String类型参数
     * @param request
     * @param name
     * @return
     */
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
