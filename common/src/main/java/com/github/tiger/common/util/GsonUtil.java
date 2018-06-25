package com.github.tiger.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.Map;

/**
 * @author liuhongming
 */
public class GsonUtil {

    private static GsonBuilder gsonBuilder;

    static {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.disableHtmlEscaping();
        gsonBuilder.serializeNulls();
    }


    /**
     * 将Json对象转换为Map对象
     *
     * @param json 需要转换的json字符串
     * @return
     */
    public static Map<String, Object> json2Map(String json) {

        Gson g = gsonBuilder.create();
        Map<String, Object> map = g.fromJson(json,
                new com.google.gson.reflect.TypeToken<Map<String, Object>>() {
                }.getType());
        return map;
    }

    public static JsonElement getJsonElement(String json) {
        return new Gson().fromJson(json, JsonElement.class);
    }

    /**
     * 将对象转换为Json字符串
     *
     * @param obj 需要转换字符串的对象
     * @return
     */
    public static String toJson(Object obj) {
        return gsonBuilder.create().toJson(obj);
    }

    public static <T> T fromJson(String json, com.google.gson.reflect.TypeToken<T> t) {
        return (T) gsonBuilder.create().fromJson(json, t.getType());
    }

    /**
     * 将Json字符串转换为t对象
     *
     * @param json 要转换的字符串
     * @param t    需要转换为的对象类型
     * @return
     */
    public static <T> T fromJson(String json, Class<T> t) {
        return gsonBuilder.create().fromJson(json, t);
    }

}
