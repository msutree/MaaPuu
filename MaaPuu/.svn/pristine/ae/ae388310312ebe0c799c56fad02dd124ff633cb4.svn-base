package com.maapuu.mereca.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * fastjson 解析json字符串
 */
public class FastJsonTools {
    /**
     * 用fastjson 将json字符串解析为一个 JavaBean
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T getPerson(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> getPersons(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    /**
     * 用fastjson 将jsonString 解析成 List<Map<String,Object>>
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> getListMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            // 两种写法
            // list = JSON.parseObject(jsonString, new
            // TypeReference<List<Map<String, Object>>>(){}.getType());
            list = JSON.parseObject(jsonString,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e){
        }
        return list;
    }

    /**
     * JSONOArray对象转化为list
     * @param array
     * @param cls
     * @return
     */
    public static <T>List<T> jsonARToList(org.json.JSONArray array, final Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list =JSON.parseArray(array.toString(),cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    /**
     * JSONObject对象转化为bean
     * @param object
     * @param cls
     * @return
     */
    public static <T>T jsonOBToBean(org.json.JSONObject object,Class<T> cls) {
        T t = null;
        try {
            t =getPerson(Object2Json(object),cls);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return t;
    }

    /**
     * json字符串转化为map
     * @param s
     * @return
     */
    public static Map stringToCollect(String s) {
        Map m=JSONObject.parseObject(s);
        return m;
    }

    /**
     * 将map转化为string
     * @param m
     * @return
     */
    public static String collectToString(Map m) {
        String s = JSONObject.toJSONString(m);
        return s;
    }

    /**
     * 从json字符串中解析出java对象
     * @author:qiuchen
     * @createTime:2012-7-8
     * @param jsonStr json格式字符串
     * @param clazz java类
     * @return clazz的实例
     */
    public static Object parseJavaObject(String jsonStr,Class clazz){
        return JSON.toJavaObject(JSON.parseObject(jsonStr),clazz);
    }

    /**
     * 从json字符串中解析出java对象
     * @author:qiuchen
     * @createTime:2012-7-8
     * @param ob 对象
     * @return clazz的实例
     */
    public static String Object2Json(Object ob){
        return JSON.toJSONString(ob,SerializerFeature.DisableCircularReferenceDetect);
    }

}  
