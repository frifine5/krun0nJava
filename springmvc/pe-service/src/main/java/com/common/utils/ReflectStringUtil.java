package com.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 利用反射返回对象的属性值（map字符串形式）
 */
public class ReflectStringUtil {

    private static Map<String, Object> propsMap = null;

    public static String toStringUtil(Object clazs, boolean isOutputNull) {
        propsMap = new HashMap<>();
        getParamAndValue(clazs, clazs.getClass(), isOutputNull);

        return propsMap.toString();
    }

    private static void getParamAndValue(Object clazs, Class<?> clazz, boolean isOutputNull){
        Class<?> sc = clazz.getSuperclass();
        Field[] sfields = sc.getDeclaredFields();
        if (sfields.length > 0) {
            getParamAndValue(clazs, sc, isOutputNull);
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if (null != f.get(clazs)||isOutputNull){
                    propsMap.put(f.getName(), f.get(clazs));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
