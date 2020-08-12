package com.example.myapplicationtest1.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
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
    public static String mapToString(Map<Integer, Integer> map) {
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * string转为map
     * @param str
     * @return
     */
    public static Map<Integer, Integer> stringToMap(String str) {
        try {
            return new ObjectMapper().readValue(str, new TypeReference<HashMap<Integer, Integer>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<Integer, Integer>();
    }

}