package com.example.myapplicationtest1.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * map集合和String相互转换
 */
public class MapStringUtil {
    /**
     * map转换为string
     * @param map
     * @return
     */
    public static String mapToString(Map<Integer, ?> map) {
        //将set集合转换为数组
        final Integer[] keyArray = map.keySet().toArray(new Integer[0]);
        //给数组排序(升序)
        Arrays.sort(keyArray);
        //因为String拼接效率会很低的，所以转用StringBuilder
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            // 参数值为空，则不参与签名 这个方法trim()是去空格
            if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
                sb.append(keyArray[i]).append(":").append(String.valueOf(map.get(keyArray[i])).trim());
            }
            if(i != keyArray.length-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }
    /**
     * string转为map
     * @param str
     * @return
     */
    public static Map<Integer, Object> stringToMap(String str) {
        //根据逗号截取字符串数组
        final String[] str1 = str.split(",");
        //创建Map对象
        final Map<Integer, Object> map = new HashMap<>();
        //循环加入map集合
        for (int i = 0; i < str1.length; i++) {
            //根据":"截取字符串数组
            final String[] str2 = str1[i].split(":");
            //str2[0]为KEY,str2[1]为值
            map.put(Integer.valueOf(str2[0]), str2[1]);
        }
        return map;
    }

}